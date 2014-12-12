/**
 * JedisUtils.java Create on 2014/12/12
 * Copyright(c) Gener-Tech Inc.
 * ALL Rights Reserved.
 */
package com.gqshao.redis.utils;

import org.apache.commons.io.IOUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author <a href="mailto:shao.gq@gener-tech.com">shaogq</a>
 * @version 1.0
 */
public class JedisUtils {
    public static JedisPoolConfig getMyDefaultJedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最小空闲数
        poolConfig.setMinIdle(1);
        // 最大空闲数
        poolConfig.setMaxIdle(10);
        // 最大连接数
        poolConfig.setMaxTotal(100);
        // 最长等待时间
        poolConfig.setMaxWaitMillis(-1);
        // 从池中返回连接前验证该连接
        poolConfig.setTestOnBorrow(true);
        // 将连接返回给池的时候是否验证该连接
        poolConfig.setTestOnReturn(true);
        // 是否启用一个闲置连接验证对象，对线程对池中的空闲连接进行扫描,只有TimeBetweenEvictionRunsMillis的值大于0才有意义
        poolConfig.setTestWhileIdle(true);
        // 闲置连接验证两次直接的间隔毫秒数
        poolConfig.setTimeBetweenEvictionRunsMillis(1800000);
        return poolConfig;
    }

    public static byte[] serialize(Object source) {
        if (source == null) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(source);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(oos);
        }
        return bos.toByteArray();
    }

    public static Object deserialize(byte[] source) {
        if (source == null) {
            return null;
        }
        ObjectInputStream oin = null;
        Object result = null;
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(source);
            oin = new ObjectInputStream(bin);
            result = oin.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(oin);
        }
        return result;
    }

    public static Object unserialize(byte[] source) {
        if (source == null) {
            return null;
        }
        return deserialize(source);
    }

    public static <T> String set(String key, Object value) {
        JedisPool pool = null;
        String result = null;
        try {
            pool = new JedisPool(JedisUtils.getMyDefaultJedisPoolConfig(), "192.168.3.98", 6379);
            Jedis jedis = pool.getResource();
            result = JedisUtils.set(jedis, key, value);
            pool.returnResource(jedis);

        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            pool.destroy();
        }
        return result;
    }

    public static <T> String set(Jedis jedis, String key, Object value) {
        return jedis.set(key.getBytes(), JedisUtils.serialize(value));
    }

    public static <T> T get(String key, Class<T> type) {
        JedisPool pool = null;
        T result = null;
        try {
            pool = new JedisPool(JedisUtils.getMyDefaultJedisPoolConfig(), "192.168.3.98", 6379);
            Jedis jedis = pool.getResource();
            result = JedisUtils.get(jedis, key, type);
            pool.returnResource(jedis);

        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.destroy();
        }
        return result;
    }

    public static <T> T get(Jedis jedis, String key, Class<T> type) {
        Object result = JedisUtils.unserialize(jedis.get(key.getBytes()));
        try {
            return (T) result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
