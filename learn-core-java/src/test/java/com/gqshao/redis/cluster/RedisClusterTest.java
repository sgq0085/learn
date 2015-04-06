package com.gqshao.redis.cluster;

import com.google.common.collect.Sets;
import com.gqshao.redis.channels.ClusterJedisPubSub;
import com.gqshao.redis.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

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
        cluster.set("foo1", "bar");
        cluster.set("foo10", "bar");
        cluster.set("foo100", "bar");
        System.out.println(cluster.get("foo"));
        cluster.close();
    }

    @Test
    public void eachNode() {
        // 测试各节点是否异常
        for (String key : nodes.keySet()) {
            Jedis jedis = null;
            try {
                jedis = nodes.get(key).getResource();
                jedis.ping();
            } catch (Exception e) {
                System.out.println("节点[" + key + "]异常");
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    @Test
    public void useMasterTest() {
        // 只从Master节点读取信息
        for (String key : nodes.keySet()) {
            Jedis jedis = null;
            try {
                jedis = nodes.get(key).getResource();
                if (StringUtils.isBlank(jedis.configGet("slaveof").get(1))) {
                    System.out.println(key + " : " + jedis.keys("foo*"));
                }
            } catch (Exception e) {
                System.out.println(key + "无法PING");
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    @Test
    public void testSubscribe() {
        for (final String key : nodes.keySet()) {
            try {
                final ClusterJedisPubSub listener = new ClusterJedisPubSub(key);
                final Jedis subscribeJedis = nodes.get(key).getResource();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(key + " :subscribe channelA.test channelB.send_message");
                        subscribeJedis.subscribe(listener, "channelA.test", "channelB.send_message");
                    }
                }).start();
                Jedis publishJedis = nodes.get(key).getResource();
                publishJedis.publish("channelA.test", "OK");
                publishJedis.publish("channelB.send_message", "Hello World!");
                listener.unsubscribe("channelA.test", "channelB.send_message");
            } catch (Exception e) {
            }
        }
    }
}
