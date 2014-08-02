package com.gqshao.redis;

import redis.clients.jedis.Jedis;

public class TestRedis {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.158.131", 6379, 1000);
        jedis.set("redis", "redis value");
        String value = jedis.get("redis");
        System.out.println("redis=" + value);
    }
}