package com.gqshao.redis.cluster.component;

import com.gqshao.redis.service.PubSubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import java.util.Map;

public class PubSubHandle extends JedisPubSub {

    protected static Logger logger = LoggerFactory.getLogger(PubSubHandle.class);

    private String node;

    private static Map<String, PubSubService> channelHandles;

    @Autowired
    private JedisCluster jedisCluster;

    @PostConstruct
    public void subscribe() {
        for (final String channel : channelHandles.keySet()) {
            final Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();
            for (final String node : nodes.keySet()) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Jedis jedis = nodes.get(node).getResource();
                                PubSubHandle handle = new PubSubHandle();
                                handle.setNode(node);
                                jedis.subscribe(handle, channel);
                            } catch (Exception e) {
                                // 任意节点损坏都会在此抛出异常
                                logger.warn("Redis {} {} 订阅消息失去连接", node, channel, e);
                            }
                        }
                    }).start();
                } catch (Exception e) {
                }
            }
        }
    }

    // 取得订阅的消息后分发
    public void onMessage(String channel, String message) {
        try {
            logger.debug("{} 消息通道 {} 得到消息 : {}", node, channel, message);
            for (String key : channelHandles.keySet()) {
                if (channel.equals(key)) {
                    channelHandles.get(key).handle(channel, message);
                }
            }
        } catch (Exception e) {
            logger.warn("{} 消息通道 {} 得到消息 : {} 处理异常", node, channel, message, e);
        }

    }

    // 初始化订阅时候的处理
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("初始化订阅Redis消息通道 :{} {} = {}", node, channel, subscribedChannels);
    }

    // 取消订阅时候的处理
    public void onUnsubscribe(String channel, int subscribedChannels) {
        logger.info("取消初始化订阅Redis消息通道 : {} {} = {}", node, channel, subscribedChannels);
    }

    public void setNode(String node) {
        this.node = node;
    }

    public void setChannelHandles(Map<String, PubSubService> channelHandles) {
        this.channelHandles = channelHandles;
    }
}
