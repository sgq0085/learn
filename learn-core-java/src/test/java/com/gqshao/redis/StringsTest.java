package com.gqshao.redis;

import org.junit.Test;

/**
 * Strings二进制数据
 */
public class StringsTest extends JedisTest {

    /**
     * set 设置一个key的value值
     */
    @Test
    public void testSet() {
        logger.info("set keys Tom : " + jedis.set("keys", "Tom"));
        logger.info("get keys : " + jedis.get("keys"));
        logger.info("del keys : " + jedis.del("keys"));
    }

    /**
     * setnx 设置value之前检查是否存在key，只有当该key不存在时才设置value
     */
    @Test
    public void testSetnx() {
        logger.info("set keysnx old : " + jedis.set("keysnx", "old"));
        logger.info("setnx keysnx ,key已存在，返回 0 : " + jedis.setnx("keysnx", "value"));
        logger.info("get keysnx :" + jedis.get("keysnx"));
        logger.info("del keysnx : " + jedis.del("keysnx"));
    }

    /**
     * setex 设置key-value并设置过期时间
     */
    @Test
    public void testSetex() throws InterruptedException {
        logger.info("setex keysex 10 10sec : " + jedis.setex("keysex", 10, "10sec"));
        logger.info("get keysex : " + jedis.get("keysex"));
        logger.info("sleep 10 sec");
        Thread.sleep(10000);
        logger.info("get keysex : " + jedis.get("keysex"));
        logger.info("del keysex : " + jedis.del("keysex"));
    }

    /**
     * setrange 从指定的offset处开始,覆盖key对应的string的value的长度
     */
    @Test
    public void testSetrange() {
        logger.info("set keyr1 \"Hello World\" : " + jedis.set("keyr1", "Hello World"));
        logger.info("setrange keyr1 6 Tom : " + jedis.setrange("keyr1", 6, "Tom"));
        logger.info("get keyr1" + jedis.get("keyr1"));
        logger.info("del keyr1 : " + jedis.del("keyr1"));
    }

    /**
     * mset 一次设置多个key，成功返回Ok
     */
    @Test
    public void testMset() {
        logger.info("mset keym1 value1 keym2 value2 keym3 value3 : " + jedis.mset("keym1", "value1", "keym2", "value2", "keym3", "value3"));
        logger.info("keys keym* : " + jedis.keys("keym*"));
        logger.info("get keym1 : " + jedis.get("keym1"));
        logger.info("del keym1 keym2 keym3 : " + jedis.del("keym1", "keym2", "keym3"));
        logger.info("keys keym* : " + jedis.keys("keym*"));
    }

    /**
     * msetnx 一次设置多个key,只要有一个key已经存在，MSETNX一个操作都不会执行
     */
    @Test
    public void testMsetnx() {
        logger.info("set keym1 old : " + jedis.set("keym1", "old"));
        logger.info("msetnx keym1 n1 keym2 n2 keym3 n3 keym4 n4 : " + jedis.msetnx("keym1", "n1", "keym2", "n2", "keym3", "n3", "keym4", "n4"));
        logger.info("keys keym* : " + jedis.keys("keym*"));
        logger.info("get keym1 : " + jedis.get("keym1"));
        logger.info("get keym4 : " + jedis.get("keym4"));
        logger.info("del keym1 : " + jedis.del("keym1"));
    }

    /**
     * getset 设置key的值，并返回key的旧值
     */
    @Test
    public void testGetset() {
        logger.info("set keygs value : " + jedis.set("keygs", "oldValue"));
        logger.info("getset keygs value : " + jedis.getSet("keygs", "newValue"));
        logger.info("get keygs : " + jedis.get("keygs"));
        logger.info("del keygs : " + jedis.del("keygs"));
    }

    /**
     * getrange 通过指定偏移量返回子字符串,左从0开始，右从-1开始 全闭集合
     */
    @Test
    public void testGetrange() {
        logger.info("set keyr 0123456789 : " + jedis.set("keyr", "0123456789"));
        logger.info("getrange keyr 0 5 : " + jedis.getrange("keyr", 0, 5));
        logger.info("getrange keyr 0 -1 :  " + jedis.getrange("keyr", 5, -1));
        logger.info("getrange keyr 0 -5 : " + jedis.getrange("keyr", 0, -5));
        logger.info("del keyr : " + jedis.del("keyr"));
    }

    /**
     * mget 获取多个key的值，key不存在返回null
     */
    @Test
    public void testMget() {
        logger.info("mset keym1 n1 keym2 n2  : " + jedis.mset("keym1", "n1", "keym2", "n2"));
        logger.info("mget keym1 keym2 keym3: " + jedis.mget("keym1", "keym2", "keym3"));
        logger.info("del keym1 keym2" + jedis.del("keym1", "keym2"));
    }

    /**
     * incr incrby value++,不是数字类型抛出异常，incr一个不存在的key，则value设置为1
     * decr decrby
     */
    @Test
    public void testInDe() {
        logger.info("incr keyi : " + jedis.incr("keyi"));
        logger.info("get keyi : " + jedis.get("keyi"));
        logger.info("set keyi a: " + jedis.set("keyi", "a"));
        try {
            logger.info("incr keyi : " + jedis.incr("keyi"));
        } catch (Exception e) {
            logger.warn("value不是数字类似抛出异常", e);
        }
        logger.info("del keyi : " + jedis.del("keyi"));

        logger.info("incrby keyint 10 : " + jedis.incrBy("keyint", 10));
        logger.info("decr keyint : " + jedis.decr("keyint"));
        logger.info("decrby keyint 10 : " + jedis.decrBy("keyint", 10));
        logger.info("del keyint : " + jedis.del("keyint"));
    }

    /**
     * append 追加value，返回新Strings长度
     */
    @Test
    public void testAppend() {
        logger.info("set keye email : " + jedis.set("keye", "email"));
        logger.info("append keye @163.com : " + jedis.append("keye", "@163.com"));
        logger.info("get keye : " + jedis.get("keye"));
        logger.info("del keye : " + jedis.del("keye"));
    }

    /**
     * strlen 取指定字符串长度 相当于length
     */
    @Test
    public void testStrlen() {
        logger.info("set keylen 0123456789 : " + jedis.set("keylen", "0123456789"));
        logger.info("strlen keylen :　" + jedis.strlen("keylen"));
        logger.info("del keylen : " + jedis.del("keylen"));
    }
}
