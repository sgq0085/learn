package com.gqshao.authentication.schedule;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.gqshao.authentication.dao.CachingShiroSessionDao;
import com.gqshao.authentication.listener.SessionUncacheListener;
import com.gqshao.redis.component.JedisUtils;
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

public class SessionUncacheJob implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SessionUncacheJob.class);

    @Autowired
    private JedisUtils jedisUtils;
    @Autowired
    private CachingShiroSessionDao sessionDao;


    // 首次执行的延迟时间
    private int initialDelay = 0;
    // 一次执行终止和下一次执行开始之间的延迟
    private int delay = 0;
    private ScheduledExecutorService scheduledExecutorService;


    private String[] channels;

    private SessionUncacheListener hand = new SessionUncacheListener();
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
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
                .setNameFormat("SessionUncacheJob-%1$d").build());

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
        }
        // 如果客户端发生改变关闭之前的监听开启新的监听
        else if (!port.equals(jedis.configGet("port").get(1))) {
            this.unsubscribed();
            this.subscribed();
        }
    }

    private void subscribed() {
        final Jedis jedis = jedisUtils.getResource();
        hand.setSessionDao(sessionDao);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jedis.subscribe(hand, channels);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jedisUtils.returnResource(jedis);
                }

            }
        });
        executor = Executors.newSingleThreadExecutor();
        executor.execute(thread);
        Jedis jedis2 = null;
        try {
            jedis2 = jedisUtils.getResource();
            port = jedis2.configGet("port").get(1);
        } finally {
            jedisUtils.returnResource(jedis2);
        }


        logger.info("监听端口为[" + port + "]的消息通道" + Arrays.toString(channels));
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
        logger.info("停止监听端口为[" + port + "]的消息通道" + Arrays.toString(channels));
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

