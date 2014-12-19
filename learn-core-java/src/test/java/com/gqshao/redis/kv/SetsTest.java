package com.gqshao.redis.kv;

import com.gqshao.redis.JedisTest;
import org.junit.Test;

/**
 * Sets 是无序集合 Sets中的value被称为member(成员)
 */
public class SetsTest extends JedisTest {

    /**
     * sadds [key] [members...] 添加一个或多个value members
     * smembers [key] 返回value members组装成Set集合
     * srem [key] [members...]指定value删除Sets中一个或多个values members并返回影响数
     * spop [key] 随机删除并返回一个元素
     * srandmember [key] 随机返回一个元素，但不删除
     */
    @Test
    public void testAddAndDel() {
        // myset
        logger.info("test sadd");
        logger.info("sadd myset a b c d : " + jedis.sadd("myset", "a", "b", "c", "d"));
        logger.info("smembers myset : " + jedis.smembers("myset") + "\n");

        // srem
        logger.info("test srem");
        logger.info("srem myset a b : " + jedis.srem("myset", "a", "b"));
        logger.info("srem myset a c : " + jedis.srem("myset", "a", "c"));
        logger.info("smembers myset : " + jedis.smembers("myset"));
        logger.info("del myset : " + jedis.del("myset") + "\n");


        // spop
        logger.info("test spop");
        logger.info("sadd myset a b c d : " + jedis.sadd("myset", "a", "b", "c", "d"));
        logger.info("spop myset : " + jedis.spop("myset"));
        logger.info("spop myset : " + jedis.spop("myset"));
        logger.info("smembers myset : " + jedis.smembers("myset"));
        logger.info("del myset : " + jedis.del("myset") + "\n");

        // srandmember
        logger.info("test srandmember");
        logger.info("sadd myset a b c d : " + jedis.sadd("myset", "a", "b", "c", "d"));
        logger.info("srandmember myset : " + jedis.srandmember("myset"));
        logger.info("srandmember myset : " + jedis.srandmember("myset"));
        logger.info("smembers myset : " + jedis.smembers("myset"));
        logger.info("del myset : " + jedis.del("myset") + "\n");
    }

    /**
     * sdiff [key1] [key...] 输出集合1 与 其他(一个或多个)集合value的差集
     * sdiffstore [dstkey] [key1] [key...] 将sdiff的差集结果保存到一个新的结合中
     *
     * sinter [key...] 输出所有集合的交集
     * sinterstore [dstkey] [key...] 将sinter的交集结果保存到一个新的结合中
     *
     * sunion  [key...] 输出所有集合的并集
     * sunionstore [dstkey] [key...] 将sunion的并集结果保存到一个新的结合中
     */
    @Test
    public void testSet() {
        logger.info("sadd myset1 1 2 3 foo bar : " + jedis.sadd("myset1", "1", "2", "3", "foo", "bar"));
        logger.info("sadd myset2 a b c foo bar : " + jedis.sadd("myset2", "a", "b", "c", "foo", "bar"));
        logger.info("sadd myset3 1 2 a : " + jedis.sadd("myset3", "1", "2", "a"));

        // sdiff sdiffstore
        logger.info("test sdiff sdiffstore");
        logger.info("sdiff myset1 myset2 myset3 : " + jedis.sdiff("myset1", "myset2", "myset3"));
        logger.info("sdiff myset2 myset1 myset3 : " + jedis.sdiff("myset2", "myset1", "myset3"));

        logger.info("sdiffstore resultset myset1 myset3 : " + jedis.sdiffstore("resultset", "myset1", "myset3"));
        logger.info("smembers resultset :　" + jedis.smembers("resultset"));
        logger.info("del resultset : " + jedis.del("resultset") + "\n");

        // sinter sinterstore
        logger.info("test sinter sinterstore");
        logger.info("sinter myset1 myset2" + jedis.sinter("myset1", "myset2"));
        logger.info("sinterstore resultset myset2 myset3 :　" + jedis.sinterstore("resultset", "myset2", "myset3"));
        logger.info("smembers resultset :　" + jedis.smembers("resultset"));
        logger.info("del resultset : " + jedis.del("resultset") + "\n");

        // sunion sunionstore
        logger.info("test sunion sunionstore");
        logger.info("sunion myset1 myset2" + jedis.sunion("myset1", "myset2"));
        logger.info("sunionstore resultset myset2 myset3 :　" + jedis.sunionstore("resultset", "myset2", "myset3"));
        logger.info("smembers resultset :　" + jedis.smembers("resultset"));
        logger.info("del resultset : " + jedis.del("resultset") + "\n");


        logger.info("del myset1 : " + jedis.del("myset1"));
        logger.info("del myset2 : " + jedis.del("myset2"));
        logger.info("del myset3 : " + jedis.del("myset3"));
    }

    /**
     * smove [srckey] [dstkey] [member] 将指定的member从集合srckey移动到集合dstkey中
     */
    @Test
    public void testSmove() {
        logger.info("sadd myset1 1 2 3 foo bar : " + jedis.sadd("myset1", "1", "2", "3", "foo", "bar"));
        logger.info("sadd myset2 a b c foo bar : " + jedis.sadd("myset2", "a", "b", "c", "foo", "bar"));
        logger.info("smove myset1 myset2 1 : " + jedis.smove("myset1", "myset2", "1"));
        logger.info("smove myset1 myset2 3 : " + jedis.smove("myset1", "myset2", "3"));
        logger.info("smembers myset1 :　" + jedis.smembers("myset1"));
        logger.info("smembers myset2 :　" + jedis.smembers("myset2"));
        logger.info("del myset1 : " + jedis.del("myset1"));
        logger.info("del myset2 : " + jedis.del("myset2"));
    }

    /**
     * smembers [key] 返回集合所有members(返回值Set)
     * scard [key] 返回集合元素个数，相当于size
     * sismember [key] [member] 测试元素是否在集合中，返回boolean，相当于contains
     */
    @Test
    public void testMember() {
        logger.info("sadd myset1 1 2 bar : " + jedis.sadd("myset1", "1", "2", "bar"));
        logger.info("sadd myset2 a b c foo bar : " + jedis.sadd("myset2", "a", "b", "c", "foo", "bar") + "\n");

        logger.info("test smembers");
        logger.info("smembers myset1 :　" + jedis.smembers("myset1"));
        logger.info("smembers myset2 :　" + jedis.smembers("myset2") + "\n");

        logger.info("test scard");
        logger.info("scard myset1 :　" + jedis.scard("myset1"));
        logger.info("scard myset2 :　" + jedis.scard("myset2") + "\n");

        logger.info("test sismember");
        logger.info("sismember : " + jedis.sismember("myset1", "1"));
        logger.info("sismember : " + jedis.sismember("myset1", "foo"));


        logger.info("del myset1 : " + jedis.del("myset1"));
        logger.info("del myset2 : " + jedis.del("myset2"));

    }


}
