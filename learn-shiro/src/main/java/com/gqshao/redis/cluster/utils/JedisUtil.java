package com.gqshao.redis.cluster.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.Map;

public class JedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    @Autowired
    private JedisCluster jedisCluster;

    public void publish(String channel, Serializable message) {
        if (StringUtils.isBlank(channel) || message == null) {
            return;
        }
        Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();
        for (String node : nodes.keySet()) {
            Jedis jedis = null;
            try {
                jedis = nodes.get(node).getResource();
                jedis.publish(channel, (String) message);
            } catch (Exception e) {
                // 任意节点损坏都会在此抛出异常
                logger.warn("发送消息到{} {} : {}失败", node, channel, message, e);
            } finally {
                JedisUtil.close(jedis);
            }
        }
    }

    public static void close(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            logger.warn("销毁连接时出现异常", e);
        }
    }
}
