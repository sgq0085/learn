package com.gqshao.redis.singleton.component;

import com.gqshao.redis.service.PubSubService;
import com.gqshao.redis.singleton.utils.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Redis消息统一处理类
 */
public class PubSubHandle extends JedisPubSub {

    protected static Logger logger = LoggerFactory.getLogger(PubSubHandle.class);

    private static Map<String, PubSubService> channelHandles;

    @Autowired
    private JedisUtil jedisUtils;

    @PostConstruct
    public void subscribe() {
        for (final String channel : channelHandles.keySet()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Jedis jedis = jedisUtils.getResource();
                        jedis.subscribe(new PubSubHandle(), channel);
                    } catch (Exception e) {
                        logger.warn("Redis消息订阅失败", e);
                    }
                }
            }).start();
        }
    }

    // 取得订阅的消息后分发
    public void onMessage(String channel, String message) {
        try {
            logger.info("从消息通道{}得到消息:{} : ", channel, message);
            for (String key : channelHandles.keySet()) {
                if (channel.equals(key)) {
                    channelHandles.get(key).handle(channel, message);
                }
            }
        } catch (Exception e) {
            logger.warn("处理来自通道 {} 的消息 {} 出现异常", channel, message, e);
        }

    }

    // 初始化订阅时候的处理  
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("初始化订阅Redis消息通道 : {} = {}", channel, subscribedChannels);
    }

    // 取消订阅时候的处理  
    public void onUnsubscribe(String channel, int subscribedChannels) {
        logger.info("取消初始化订阅Redis消息通道 : {} = {}", channel, subscribedChannels);
    }

    public void setChannelHandles(Map<String, PubSubService> channelHandles) {
        this.channelHandles = channelHandles;
    }
}
