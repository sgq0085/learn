package com.gqshao.redis.component;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.TaskUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 消息通道维护类 本身作为一个线程由JDK维护Job运行时间
 * 每隔一段时间测试当前连接是否能PING的通
 * 测试SubscribeRunnable/PSubscribeRunnable其中一种情况
 */
public class SubDaemonJob implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SubDaemonJob.class);

    @Autowired
    private JedisUtils jedisUtils;

    // 首次执行的延迟时间
    private int initialDelay = 0;
    // 一次执行终止和下一次执行开始之间的延迟
    private int delay = 0;
    private ScheduledExecutorService scheduledExecutorService;


    private String[] channels;
    private MyJedisPubSub hand = new MyJedisPubSub();
    private String port = null;
    private ExecutorService executor;

    /**
     * 启动该线程，并按间隔时间测试连接正确性
     *
     * @throws Exception
     */
    @PostConstruct
    public void start() throws Exception {
        Validate.isTrue(delay > 0);

        // 任何异常不会中断schedule执行, 由Spring TaskUtils的LOG_AND_SUPPRESS_ERROR_HANDLER進行处理
        Runnable task = TaskUtils.decorateTaskWithErrorHandler(this, null, true);

        // 创建单线程的SechdulerExecutor,并用guava的ThreadFactoryBuilder设定生成线程的名称
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat(
                "SubDaemonJob-%1$d").build());

        // scheduleAtFixedRate() 固定任务两次启动之间的时间间隔.
        // scheduleWithFixedDelay() 固定任务结束后到下一次启动间的时间间隔.
        scheduledExecutorService.scheduleWithFixedDelay(task, initialDelay, delay, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        Jedis jedis = jedisUtils.getResource();

        if (jedis == null) {
            port = null;
            logger.info("无法得到Redis连接，订阅消息失败");
        } else if (port == null || !hand.isSubscribed()) {
            this.subscribed();
            logger.info("订阅Redis消息成功port[{}]", port);
        }
        // 如果客户端发生改变关闭之前的监听开启新的监听
        else if (!port.equals(jedis.configGet("port").get(1))) {
            this.unsubscribed();
            this.subscribed();
            logger.info("订阅Redis消息成功port[{}]", port);
        }
    }

    private void subscribed() {
        final Jedis jedis = jedisUtils.getResource();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jedis.subscribe(hand, channels);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        executor = Executors.newSingleThreadExecutor();
        executor.execute(thread);
        port = jedis.configGet("port").get(1);
        logger.info("启动对" + port + "的" + Arrays.toString(channels) + "监听");
    }

    private void unsubscribed() {
        hand.unsubscribe(channels);
        try {
            executor.shutdownNow();
            logger.info("executor.shutdownNow");
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.warn("Pool did not terminated");
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        logger.info("停止对" + port + "的" + Arrays.toString(channels) + "监听");
        port = null;
    }

    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setChannels(String[] channels) {
        this.channels = channels;
    }
}
