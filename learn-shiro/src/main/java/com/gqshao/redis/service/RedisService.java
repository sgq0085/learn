package com.gqshao.redis.service;

import com.gqshao.redis.component.JedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;


@SuppressWarnings("unchecked")
@Service
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private JedisUtils jedisUtils;

    public void test() {
        Jedis jedis = null;
        try {
            jedis = jedisUtils.getResource();
            jedis.set("newmykey", "value");
            logger.info("res : " + jedis.get("newmykey"));
            jedis.del("newmykey");
        } catch (Exception e) {

        } finally {
            jedisUtils.returnResource(jedis);
        }

    }

}
