package com.gqshao.redis.connection;


import com.google.common.collect.Lists;
import com.gqshao.redis.utils.JedisUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.Arrays;
import java.util.List;


/**
 * Jedis 独有的分布式
 * 根据List<JedisShardInfo>中Redis顺序和key，指定固定的Redis
 * 扩容时使用 Pre-Sharding
 */
public class ShardedJedisTest {

    protected static Logger logger = LoggerFactory.getLogger(ShardedJedisTest.class);

    private static ShardedJedisPool pool = null;
    protected ShardedJedis jedis = null;


    @BeforeClass
    public static void createPool() {
        JedisPoolConfig poolConfig = JedisUtils.getMyDefaultJedisPoolConfig();
//        List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo("192.168.3.98", 6379)
//                , new JedisShardInfo("192.168.3.98", 6380));
        List<JedisShardInfo> shards = Lists.newArrayList();
        JedisShardInfo info1 = new JedisShardInfo("192.168.3.98", 6379);
        JedisShardInfo info2 = new JedisShardInfo("192.168.3.98", 6380);
        // 添加的顺序有意义
        shards.add(info1);
        shards.add(info2);
        pool = new ShardedJedisPool(poolConfig, shards);
    }

    @Before
    public void getResource() {
        jedis = pool.getResource();
    }

    @After
    public void destroyJedis() {
        pool.returnResource(jedis);
    }

    @AfterClass
    public static void destroyPool() {
        pool.destroy();
    }

    /**
     * 不同的key分配根据List<JedisShardInfo>中的顺序，分配不同的Redis连接
     */
    @Test
    public void testSet() {
        for (int i = 0; i < 20; i++) {
            try {
                logger.info(i + " - " + jedis.getShard("" + i).getClient().getHost()
                        + " : " + jedis.getShard(i + "").getClient().getPort());
                logger.info(jedis.set("" + i, "true"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修改List<JedisShardInfo>中的顺序，无法得到结果，确定该顺序是有意义的
     */
    @Test
    public void testGet() {
        for (int i = 0; i < 20; i++) {
            try {
                logger.info(i + " - " + jedis.getShard(i + "").getClient().getHost()
                        + ":" + jedis.getShard(i + "").getClient().getPort() + " ? " + jedis.get("" + i));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
