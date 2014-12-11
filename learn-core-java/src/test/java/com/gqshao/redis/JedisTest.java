package com.gqshao.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {

    @Test
    public void test() {
        Jedis jedis = new Jedis("192.168.3.98", 6379, 1000);
        jedis.set("redis", "redis value");
        String value = jedis.get("redis");
        System.out.println("redis=" + value);
    }
}