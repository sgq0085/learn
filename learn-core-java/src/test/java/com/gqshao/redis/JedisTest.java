package com.gqshao.redis;

import com.gqshao.redis.utils.JedisUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.PostConstruct;

public class JedisTest {

    private static JedisPool pool;
    protected Jedis jedis = null;
    protected static Logger logger = LoggerFactory.getLogger(JedisTest.class);

    @BeforeClass
    public static void init() {
        JedisPoolConfig poolConfig = JedisUtils.getMyDefaultJedisPoolConfig();
        pool = new JedisPool(poolConfig, "192.168.3.98", 6379);
        System.out.println("----------------------create resource----------------------");
    }

    @AfterClass
    public static void destroy() {
        pool.destroy();
        System.out.println("----------------------destroy resource---------------------");
    }

    @Before
    public void initJedis() {
        jedis = pool.getResource();
        System.out.println("----------------------get resource-------------------------");
    }

    @After
    public void destroyJedis() {
        pool.returnResource(jedis);
        System.out.println("----------------------return resource----------------------");
    }

}