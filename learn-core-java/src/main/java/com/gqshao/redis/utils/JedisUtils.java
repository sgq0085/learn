package com.gqshao.redis.utils;

import org.apache.commons.io.IOUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    public static <T> T deserialize(byte[] source) {
        if (source == null) {
            return null;
        }
        ObjectInputStream oin = null;
        T result = null;
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(source);
            oin = new ObjectInputStream(bin);
            result = (T) oin.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(oin);
        }
        return result;
    }

    public static <T> T unserialize(byte[] source) {
        return deserialize(source);
    }


}
