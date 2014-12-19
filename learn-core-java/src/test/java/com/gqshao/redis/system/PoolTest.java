package com.gqshao.redis.system;

import com.google.common.collect.Lists;
import com.gqshao.redis.domin.SerializableBean;
import com.gqshao.redis.utils.JedisUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.List;

public class PoolTest {

    private static DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");

    protected static Logger logger = LoggerFactory.getLogger(PoolTest.class);

    @Test
    public void testPool() {
        JedisPoolConfig poolConfig = JedisUtils.getMyDefaultJedisPoolConfig();
        JedisPool pool = new JedisPool(poolConfig, "192.168.3.98", 6379);
        try {
            Jedis jedis = pool.getResource();
            logger.info("得到连接");
            jedis.set("redis", "redis value");
            String value = jedis.get("redis");
            logger.info("redis=" + value);
            jedis.del("redis");
            pool.returnResource(jedis);
            logger.info("返回连接");
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.destroy();
        }
    }


    @Test
    public void testSerializable() {
        JedisPoolConfig poolConfig = JedisUtils.getMyDefaultJedisPoolConfig();
        JedisPool pool = new JedisPool(poolConfig, "192.168.3.98", 6379);
        try {
            Jedis jedis = pool.getResource();
            logger.info("得到连接");
            // 写
            List<SerializableBean> beans = Lists.newArrayList();
            SerializableBean b1 = new SerializableBean("id1", "n1", "v1", DateTime.now().toDate(), true);
            SerializableBean b2 = new SerializableBean("id2", "n2", "v2", DateTime.now().toDate(), false);
            beans.add(b1);
            beans.add(b2);
            String key = "beans";
            String setRes = JedisUtils.set(jedis, key, beans);
            logger.info("setRes : " + setRes);

            // 读
            List<SerializableBean> res = JedisUtils.get(jedis, key, new ArrayList<SerializableBean>().getClass());
            if (res != null) {
                for (SerializableBean bean : res) {
                    logger.info("id =" + bean.getId() + ", name : " + bean.getName() + ", value + " + bean.getValue()
                            + format.print(bean.getDate().getTime()) + ", isTrue : " + bean.getIsTrue());
                }
            }

            //删
            jedis.del(key);
            pool.returnResource(jedis);
            logger.info("返回连接");
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.destroy();
        }
    }

    @Test
    public void testJedisUtilsSetAndGet() {
        List<SerializableBean> beans = Lists.newArrayList();
        SerializableBean b1 = new SerializableBean("id1", "n1", "v1", DateTime.now().toDate(), true);
        SerializableBean b2 = new SerializableBean("id2", "n2", "v2", DateTime.now().toDate(), false);
        beans.add(b1);
        beans.add(b2);
        String key = "beans";
        String setRes = JedisUtils.set(key, beans);
        logger.info("setRes : " + setRes);
        List<SerializableBean> res = JedisUtils.get(key, new ArrayList<SerializableBean>().getClass());
        for (SerializableBean bean : res) {
            logger.info("id =" + bean.getId() + ", name : " + bean.getName() + ", value + " + bean.getValue()
                    + format.print(bean.getDate().getTime()) + ", isTrue : " + bean.getIsTrue());
        }


        // del
        Jedis jedis = new Jedis("192.168.3.98", 6379, 1000);
        jedis.del(key);
    }
}
