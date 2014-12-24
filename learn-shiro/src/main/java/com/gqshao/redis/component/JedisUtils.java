package com.gqshao.redis.component;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

@Component
public class JedisUtils {

    private static final Logger logger = LoggerFactory.getLogger(JedisUtils.class);

    @Autowired
    private JedisPool jedisPool1;

    @Autowired
    private JedisPool jedisPool2;

    private JedisPool pool;

    @Value("${redis1.host}")
    private String host1;

    @Value("${redis1.port}")
    private Integer port1;

    @Value("${redis2.host}")
    private String host2;

    @Value("${redis2.port}")
    private Integer port2;

    @PostConstruct
    public void init() {
        this.setPool();
    }

    private Jedis setPool() {
        Jedis jedis1 = this.getResource(jedisPool1);
        Jedis jedis2 = this.getResource(jedisPool2);

        if (jedis1 != null) {
            // 如果Redis节点1是其它节点的slave，将其升级为master
            String slaveof1 = jedis1.configGet("slaveof").get(1);
            if (StringUtils.isNotBlank(slaveof1)) {
                jedis1.slaveofNoOne();
            }
            pool = jedisPool1;
            if (jedis2 != null) {
                // 如果Redis节点2存在，设置为节点1的slave
                jedis2.slaveof(host1, port1);
            }
            return jedis1;
        } else if (jedis2 != null) {
            pool = jedisPool2;
            // 如果Redis节点2是其它节点的slave，将其升级为master
            String slaveof2 = jedis2.configGet("slaveof").get(1);
            if (StringUtils.isNotBlank(slaveof2)) {
                jedis2.slaveofNoOne();
            }
            return jedis2;
        }
        return null;
    }

    private Jedis getResource(JedisPool jedisPool) {
        try {
            for (int i = 0; i < 5; i++) {
                Jedis jedis = jedisPool.getResource();
                String ping = jedis.ping();
                if ("PONG".equals(ping)) {
                    return jedis;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public Jedis getResource() {
        Jedis jedis = null;
        if (pool != null) {
            jedis = this.getResource(pool);
        }
        if (jedis == null) {
            jedis = this.setPool();
        }
        return jedis;
    }

    public void destroy(Jedis jedis) {
        try {
            pool.returnResource(jedis);
        } catch (Exception e) {
            logger.warn("销毁连接时出现异常", e);
            e.printStackTrace();
        }
    }


}
