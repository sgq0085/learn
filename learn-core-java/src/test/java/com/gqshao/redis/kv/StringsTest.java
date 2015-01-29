package com.gqshao.redis.kv;

import com.gqshao.redis.JedisTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Strings二进制数据
 */
public class StringsTest extends JedisTest {

    protected static Logger logger = LoggerFactory.getLogger(StringsTest.class);

    /**
     * set [key] [value] 设置一个key的value值
     * get [key] 根据key返回value
     * del [key] 删除
     */
    @Test
    public void testSet() {
        logger.info("set keys Tom : " + jedis.set("keys", "Tom"));
        logger.info("get keys : " + jedis.get("keys"));
        logger.info("del keys : " + jedis.del("keys"));
    }

    /**
     * setnx [key] [value] 当该key不存在时设置value,nx not exist
     */
    @Test
    public void testSetnx() {
        logger.info("set keysnx old : " + jedis.set("keysnx", "old"));
        logger.info("setnx keysnx ,key已存在，返回 0 : " + jedis.setnx("keysnx", "value"));
        logger.info("get keysnx :" + jedis.get("keysnx"));
        logger.info("del keysnx : " + jedis.del("keysnx"));
    }

    /**
     * setex [key] [seconds] [value] 设置key-value并设置过期时间，时间单位为秒
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
     * setrange [key] [offset] [value] 从指定的offset(从0开始计算)处开始,按照给定的value覆盖(index的值会被覆盖)
     */
    @Test
    public void testSetrange() {
        logger.info("set keyr1 \"Hello World\" : " + jedis.set("keyr1", "Hello World"));
        logger.info("setrange keyr1 6 Tom : " + jedis.setrange("keyr1", 6, "Tom"));
        logger.info("get keyr1 : " + jedis.get("keyr1"));
        logger.info("del keyr1 : " + jedis.del("keyr1"));
    }

    /**
     * mset [key1] [value1] [key2] [value2] ... 一次设置多个key，成功返回Ok
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
     * msetnx [key1] [value1] [key2] [value2] ...
     * 一次设置多个key,只要有一个key已经存在，msetnx一个操作都不会执行
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
     * getset [key] [value] 设置key的值，并返回key的旧值
     */
    @Test
    public void testGetset() {
        logger.info("set keygs value : " + jedis.set("keygs", "oldValue"));
        logger.info("getset keygs value : " + jedis.getSet("keygs", "newValue"));
        logger.info("get keygs : " + jedis.get("keygs"));
        logger.info("del keygs : " + jedis.del("keygs"));
    }

    /**
     * getrange [key] [startOffset] [endOffset]
     * 通过指定偏移量返回子字符串,左从0开始，右从-1开始 全闭集合
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
     * mget [key1] [key2] ...获取多个key的值List，key不存在返回null
     */
    @Test
    public void testMget() {
        logger.info("mset keym1 n1 keym3 n3  : " + jedis.mset("keym1", "n1", "keym3", "n3"));
        logger.info("mget keym1 keym2 keym3: " + jedis.mget("keym1", "keym2", "keym3"));
        logger.info("del keym1 keym3" + jedis.del("keym1", "keym3"));
    }

    /**
     * incr [key] value++,不是数字类型抛出异常，incr一个不存在的key，则value设置为1
     * incrby [key] [value] value-=[value],incrby一个不存在的key，则value设置为[value]
     * decr [key] value-- decr一个不存在的key，则value设置为1
     * decrby [key] [value] value-=[value],decrby一个不存在的key，则value设置为[value]
     */
    @Test
    public void testIncrDecr() {
        // incr
        logger.info("test incr");
        logger.info("incr keyi : " + jedis.incr("keyi"));
        logger.info("get keyi : " + jedis.get("keyi") + "\n");

        // value is not an integer
        logger.info("test value is not an integer");
        logger.info("set keyi a: " + jedis.set("keyi", "a"));
        try {
            logger.info("incr keyi : " + jedis.incr("keyi"));
        } catch (Exception e) {
            logger.warn("value不是数字类似抛出异常", e);
        }
        logger.info("del keyi : " + jedis.del("keyi") + "\n");

        logger.info("test incrby");
        logger.info("incrby keyint 10 : " + jedis.incrBy("keyint", 10) + "\n");

        logger.info("test decr");
        logger.info("decr keyint : " + jedis.decr("keyint") + "\n");

        logger.info("test decrby");
        logger.info("decrby keyint 10 : " + jedis.decrBy("keyint", 10));
        logger.info("decrby keyint2 10 : " + jedis.decrBy("keyint2", 10));
        logger.info("del keyint : " + jedis.del("keyint"));
        logger.info("del keyint2 : " + jedis.del("keyint2"));
    }

    /**
     * append [key] [value] 在原value后追加value，相当于value=value+"[value]"，返回新Strings长度
     */
    @Test
    public void testAppend() {
        logger.info("set keye email : " + jedis.set("keye", "email"));
        logger.info("append keye @163.com : " + jedis.append("keye", "@163.com"));
        logger.info("get keye : " + jedis.get("keye"));
        logger.info("del keye : " + jedis.del("keye"));
    }

    /**
     * strlen [key] 取指定字符串长度 相当于length
     */
    @Test
    public void testStrlen() {
        logger.info("set keylen 0123456789 : " + jedis.set("keylen", "0123456789"));
        logger.info("strlen keylen :　" + jedis.strlen("keylen"));
        logger.info("del keylen : " + jedis.del("keylen"));
    }
}
