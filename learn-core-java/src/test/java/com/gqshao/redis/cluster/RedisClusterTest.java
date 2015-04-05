package com.gqshao.redis.cluster;

import com.google.common.collect.Sets;
import com.gqshao.redis.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RedisClusterTest {

    private static JedisCluster cluster = null;
    private Map<String, JedisPool> nodes = null;

    @BeforeClass
    public static void init() {
        Set<HostAndPort> jedisClusterNodes = Sets.newHashSet();
        jedisClusterNodes.add(new HostAndPort("192.168.158.128", 6380));
        jedisClusterNodes.add(new HostAndPort("192.168.158.128", 6381));
        jedisClusterNodes.add(new HostAndPort("192.168.158.128", 6382));
        jedisClusterNodes.add(new HostAndPort("192.168.158.128", 7380));
        jedisClusterNodes.add(new HostAndPort("192.168.158.128", 7381));
        jedisClusterNodes.add(new HostAndPort("192.168.158.128", 7382));
        cluster = new JedisCluster(jedisClusterNodes, 0, JedisUtils.getMyDefaultJedisPoolConfig());
    }

    @Before
    public void beforeMethod() {
        nodes = cluster.getClusterNodes();
    }


    /**
     * pool.destroy()
     */
    @AfterClass
    public static void destroy() {
        cluster.close();
    }

    @Test
    public void easyTest() {
        // 简单测试
        cluster.set("foo", "bar");
        System.out.println(cluster.get("foo"));
        cluster.close();
    }

    @Test
    public void eachNode() {
        // 测试各节点是否异常
        for (String key : nodes.keySet()) {
            try {
                Jedis jedis = nodes.get(key).getResource();
                jedis.ping();
                jedis.close();
            } catch (Exception e) {
                System.out.println("节点[" + key + "]异常");
            }
        }
    }

    @Test
    public void useMasterTest() {
        // 只从Master节点读取信息
        for (String key : nodes.keySet()) {
            try {
                Jedis jedis = nodes.get(key).getResource();
                if (StringUtils.isBlank(jedis.configGet("slaveof").get(1))) {
                    System.out.println(jedis.keys("foo*"));
                }
            } catch (Exception e) {
                System.out.println(key + "无法PING");
            }
        }
    }
}
