package com.gqshao.redis.singleton.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

public class JedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    @Autowired
    private JedisPool jedisPool;

    public Jedis getResource() {
        try {
            return jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void close(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            logger.warn("销毁连接时出现异常", e);
            e.printStackTrace();
        }
    }

    public void publish(String channel, Serializable message) {
        if (StringUtils.isBlank(channel) || message == null) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = this.getResource();
            jedis.publish(channel, (String) message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(jedis);
        }
    }


}
