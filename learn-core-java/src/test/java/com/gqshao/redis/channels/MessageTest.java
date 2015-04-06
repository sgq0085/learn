package com.gqshao.redis.channels;


import com.gqshao.redis.JedisTestBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 发布/订阅
 */
public class MessageTest extends JedisTestBase {

    protected static Logger logger = LoggerFactory.getLogger(MessageTest.class);

    /**
     * SUBSCRIBE [channel...] 订阅一个匹配的通道
     * PSUBSCRIBE [pattern...] 订阅匹配的通道
     * PUBLISH [channel] [message] 将value推送到channelone通道中
     * UNSUBSCRIBE [channel...] 取消订阅消息
     * PUNSUBSCRIBE [pattern ...] 取消匹配的消息订阅
     * web环境中可以编写一个JedisPubSub 继承 @see redis.clients.jedis.JedisPubSub来实现监听
     * Jedis中通过使用 JedisPubSub.UNSUBSCRIBE/PUNSUBSCRIBE 来取消订阅
     */
    @Test
    public void testSubscribe() {
        final SingletonJedisPubSub listener = new SingletonJedisPubSub();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("subscribe channelA.test channelB.send_message");
                jedis.subscribe(listener, "channelA.test", "channelB.send_message");
            }
        });
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(thread);

        // 测试发送
        Jedis pubJedis = pool.getResource();
        logger.info("publish channelA.test OK : " + pubJedis.publish("channelA.test", "OK"));
        logger.info("publish channelB.send_message \"Hello World!\" : " + pubJedis.publish("channelB.send_message", "Hello World!"));
        listener.unsubscribe("channelA.test", "channelB.send_message");
        try {
            executor.shutdownNow();
            logger.info("executor.shutdownNow");
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.warn("Pool did not terminated");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        logger.info("完成subscribe测试");
    }


    /**
     * SUBSCRIBE channelone 订阅一个通道
     * PSUBSCRIBE channel* 订阅一批通道
     * PUBLISH channelone value 将value推送到channelone通道中
     * web环境中可以编写一个Listener 继承 @see redis.clients.jedis.JedisPubSub来实现监听
     */
    @Test
    public void testPsubscribe() {
        final SingletonJedisPubSub listener = new SingletonJedisPubSub();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("psubscribe channel*");
                jedis.psubscribe(listener, "channel*");
            }
        });
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(thread);

        // 测试发送
        Jedis pubJedis = pool.getResource();

        logger.info("publish channelA.test OK: " + pubJedis.publish("channelA.test", "OK"));
        logger.info("publish channelB.send_message \"Hello World!\"" + pubJedis.publish("channelB.send_message", "Hello World!"));

        pool.returnResource(pubJedis);
        listener.punsubscribe();
        try {
            executor.shutdownNow();
            logger.info("executor.shutdownNow");
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.warn("Pool did not terminated");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        logger.info("完成psubscribe测试");
        logger.info("publish channelA.test OK: " + pubJedis.publish("channelA.test", "OK"));
    }

}
