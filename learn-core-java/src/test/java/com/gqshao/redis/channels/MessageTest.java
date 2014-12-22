package com.gqshao.redis.channels;


import com.gqshao.redis.JedisTest;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class MessageTest extends JedisTest {

    /**
     * SUBSCRIBE channelone 订阅一个通道
     * PSUBSCRIBE channel* 订阅一批通道
     * PUBLISH channelone value 将value推送到channelone通道中
     * web环境中可以编写一个Listener 继承 @see redis.clients.jedis.JedisPubSub来实现监听
     */
    @Test
    public void testSubscribe() {
        final MyJedisPubSub listener = new MyJedisPubSub();
        new Thread(new Runnable() {
            @Override
            public void run() {
                jedis.subscribe(listener, "channelA.test", "channelB.send_message");
            }
        }).start();
        Jedis pubJedis = pool.getResource();
        pubJedis.publish("channelA.test", "OK");
        pubJedis.publish("channelB.send_message", "Hello World!");
    }


    /**
     * SUBSCRIBE channelone 订阅一个通道
     * PSUBSCRIBE channel* 订阅一批通道
     * PUBLISH channelone value 将value推送到channelone通道中
     * web环境中可以编写一个Listener 继承 @see redis.clients.jedis.JedisPubSub来实现监听
     */
    @Test
    public void testPsubscribe() {
        final MyJedisPubSub listener = new MyJedisPubSub();
        new Thread(new Runnable() {
            @Override
            public void run() {
                jedis.psubscribe(listener, "channel*");
            }
        }).start();
        Jedis pubJedis = pool.getResource();
        pubJedis.publish("channelA.test", "OK");
        pubJedis.publish("channelB.send_message", "Hello World!");
    }

}
