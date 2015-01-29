package com.gqshao.redis.kv;

import com.google.common.collect.Maps;
import com.gqshao.redis.JedisTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Tuple;

import java.util.Map;
import java.util.Set;

/**
 * Sorted Sets是Sets的升级版本，由score(优先级)和value组成，即集合中每个元素都关联一个double类型的score
 * Sorted Sets是List和Hashes的混合体，Sorted Sets最经常的使用方式是作为索引来使用，把要排序的字段作为按score存储，元素的id作为value
 * Rank与Lists中index(下标)，功能一致，正序(从左到右，从上到下)从0开始,逆序(从右到左，从下至上)从-1开始
 * 可以有相同的score值
 */
public class SortedSetsTest extends JedisTest {

    protected static Logger logger = LoggerFactory.getLogger(SortedSetsTest.class);

    /**
     * 添加操作
     * <p/>
     * zadd [key] [score] [member] 向集合中添加元素，score可以任意指定负数、零、正数，如果member则更新score
     * zrem [key] [member...] 删除集合中元素，可同时删除多个
     * zincrby [key] [increment] [member] 如果member存在，score+=increment，如果member不存在则添加member，score=increment
     */
    @Test
    public void testZadd() {
        logger.info("test zadd");
        logger.info("zadd myzset 0 zero : " + jedis.zadd("myzset", 0, "zero"));
        Map<String, Double> memberScore = Maps.newHashMap();
        memberScore.put("foo", -1.0);
        memberScore.put("bar", -1.5);
        memberScore.put("one", 1d);
        logger.info("zadd myzset -1 foo -1.5 bar 1 one: " + jedis.zadd("myzset", memberScore));

        logger.info("zrange myzset 0 -1 withscores : ");
        Set<Tuple> myzset = jedis.zrangeWithScores("myzset", 0, -1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }
        logger.info("\n");
        logger.info("test zadd 更新foo的score");
        logger.info("zadd myzset 10 foo : " + jedis.zadd("myzset", 10, "foo"));
        logger.info("zrange myzset 0 -1 withscores : ");
        myzset = jedis.zrangeWithScores("myzset", 0, -1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }

        logger.info("\n");
        logger.info("test zrem");
        logger.info("zrem myzset bar foo: " + jedis.zrem("myzset", "bar", "foo"));
        logger.info("zrange myzset 0 -1 withscores : ");
        myzset = jedis.zrangeWithScores("myzset", 0, -1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }
        logger.info("\n");
        logger.info("test zincrby");
        logger.info("zincrby myzset -1 one: " + jedis.zincrby("myzset", -1, "one"));
        logger.info("zincrby myzset 1.5 foo: " + jedis.zincrby("myzset", 1.5, "foo"));
        logger.info("zrange myzset 0 -1 withscores : ");
        myzset = jedis.zrangeWithScores("myzset", 0, -1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }

        logger.info("del myzset : " + jedis.del("myzset"));
    }

    /**
     * 查询操作
     * <p/>
     * 通过下标
     * zrange [key] [start] [end] 从正向指定member下标返回集合元素（返回值Set<String）,可以在最后输入withscores(返回值为Set<Tuple>)
     * zrevrange [key] [start] [end] 从逆向(从右向左)指定member下标返回集合元素（返回值Set<String）,可以在最后输入withscores(返回值为Set<Tuple>)
     * <p/>
     * 通过顺序
     * zrank [key] [member] 返回member的正序排序数，相当于正序下标
     * zrevrank [key] [member] 返回member的逆向排序数，不同于逆序下标，仍是从0、1、2这样的数
     * <p/>
     * 通过score(优先级)
     * zrangebyscore [key] [min] [max] 指定集合score的最小值和最大值，返回member
     * <p/>
     * score顺序+字典范围
     *
     * zrangebylex [key] [min] [max] 按照score顺序，判断member是否在给定字符区间，遇到第一个不符合的情况后返回之前找到的结果
     * min可以用"-"代替，表示从开始，"("开区间，"["闭区间，比如想要包含zero可以用[zero或比ze顺序大的(zh
     * 顺序比较 负数<0<正数，数字<字母, 字母顺序按字母表排序
     * 如果想只通过字典范围判断，需要把score设为相同值(比如都为0)
     */
    @Test
    public void testRank() {
        Map<String, Double> memberScore = Maps.newHashMap();
        memberScore.put("minus two", -2d);
        memberScore.put("minus one", -1d);
        memberScore.put("zero", 0.0);
        memberScore.put("one", 1.0);
        memberScore.put("two", 2.0);
        logger.info("zadd myzset -2 \"minus two\" -1 \"minus one\" 0 zero 1 one 2 two : "
                + jedis.zadd("myzset", memberScore) + "\n");

        // zrange
        logger.info("zrange myzset 0 -1 withscores : ");
        Set<Tuple> myzset = jedis.zrangeWithScores("myzset", 0, -1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }

        // zrevrange
        logger.info("\n");
        logger.info("zrevrange myzset 0 -1 withscores : ");
        myzset = jedis.zrevrangeWithScores("myzset", 0, -1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }
        logger.info("zrevrange myzset 0 3 withscores : ");
        myzset = jedis.zrevrangeWithScores("myzset", 0, 3);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }

        // zrank
        logger.info("\n");
        logger.info("test zrank");
        logger.info("zrank myzset \"minus two\" : " + jedis.zrank("myzset", "minus two"));
        logger.info("zrank myzset zero : " + jedis.zrank("myzset", "zero"));

        // zrevrank
        logger.info("\n");
        logger.info("test zrevrank");
        logger.info("zrevrank myzset \"minus two\" : " + jedis.zrevrank("myzset", "minus two"));
        logger.info("zrevrank myzset zero : " + jedis.zrevrank("myzset", "zero"));
        logger.info("zrevrank myzset two : " + jedis.zrevrank("myzset", "two"));

        // zrangebyscore
        logger.info("\n");
        logger.info("test zrangebyscore");
        logger.info("zrangebyscore myzset -1 1 withscores : ");
        myzset = jedis.zrangeByScoreWithScores("myzset", -1, 1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }

        // zrangebylex

        logger.info("\n");
        logger.info("test zrangebylex");
        memberScore = Maps.newHashMap();
        memberScore.put("-10", -10.0);
        memberScore.put("10", 10.0);
        logger.info("zadd myzset -10 -10 10 10 : "
                + jedis.zadd("myzset", memberScore) + "\n");
        logger.info("zrangebylex myzset （-10 （z : ");
        Set<String> bylexRex = jedis.zrangeByLex("myzset", "(-10", "(z");
        for (String res : bylexRex) {
            logger.info(res);
        }
        logger.info("10、one、two虽然在给定(10 (z区间中，但由于score比zero大，查询在遇到zero后不满足条件就返回了");
        logger.info("zrangebylex myzset [-10 (zh : ");
        bylexRex = jedis.zrangeByLex("myzset", "[-10", "(zh");
        for (String res : bylexRex) {
            logger.info(res);
        }

        logger.info("del myzset : " + jedis.del("myzset"));
    }

    /**
     * zrangebyscore [key] [min] [max] 指定集合score的最小值和最大值，返回member
     * zcount [key] [min] [max] 指定集合score的最小值和最大值，统计member数量
     * zcard [key] 统计集合中member个数
     * zscore [key] [member] 返回member的score
     */
    @Test
    public void testScore() {
        Map<String, Double> memberScore = Maps.newHashMap();
        memberScore.put("minus two", -2d);
        memberScore.put("minus one", -1d);
        memberScore.put("zero", 0.0);
        memberScore.put("one", 1.0);
        memberScore.put("two", 2.0);
        logger.info("zadd myzset -2 \"minus two\" -1 \"minus one\" 0 zero 1 one 2 two : "
                + jedis.zadd("myzset", memberScore) + "\n");

        // zrangebyscore
        logger.info("zrangebyscore myzset -1 1 withscores : ");
        Set<Tuple> myzset = jedis.zrangeByScoreWithScores("myzset", -1, 1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }

        // zcount
        logger.info("\n");
        logger.info("zcount");
        logger.info("zcount myzset -1 1 withscores : " + jedis.zcount("myzset", -1, 1) + "\n");

        // zcard
        logger.info("zcard");
        logger.info("zcard myzset : " + jedis.zcard("myzset") + "\n");

        logger.info("zscore");
        logger.info("zscore myzset one: " + jedis.zscore("myzset", "one") + "\n");


        logger.info("del myzset : " + jedis.del("myzset"));

    }

    /**
     * zrem [key] [member...] 删除集合中元素，可同时删除多个
     * zremrangebyrank [key] [start] [end] 根据下标删除元素
     * zremrangebysocre [key] [start] [end] 根据给定score区间删除元素
     */
    @Test
    public void testRem() {
        Map<String, Double> memberScore = Maps.newHashMap();
        memberScore.put("minus two", -2d);
        memberScore.put("minus one", -1d);
        memberScore.put("zero", 0.0);
        memberScore.put("one", 1.0);
        memberScore.put("two", 2.0);
        memberScore.put("three", 3.0);
        logger.info("zadd myzset -2 \"minus two\" -1 \"minus one\" 0 zero 1 one 2 two 3 three : "
                + jedis.zadd("myzset", memberScore));
        logger.info("zrange myzset 0 -1 withscores : ");
        Set<Tuple> myzset = jedis.zrangeWithScores("myzset", 0, -1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }


        // zremrangebyrank
        logger.info("\n");
        logger.info("test zremrangebyrank");
        logger.info("zremrangebyrank myzset 0 2 : " + jedis.zremrangeByRank("myzset", 0, 2));
        logger.info("zrange myzset 0 -1 withscores : ");
        myzset = jedis.zrangeWithScores("myzset", 0, -1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }

        // 重建myzset
        logger.info("del myzset : " + jedis.del("myzset"));
        logger.info("zadd myzset -2 \"minus two\" -1 \"minus one\" 0 zero 1 one 2 two 3 three : "
                + jedis.zadd("myzset", memberScore) + "\n");

        // zremrangebyscore
        logger.info("test zremrangebyscore");
        logger.info("zremrangebyscore myzset 0 2 : " + jedis.zremrangeByScore("myzset", 0, 2));
        logger.info("zrange myzset 0 -1 withscores : ");
        myzset = jedis.zrangeWithScores("myzset", 0, -1);
        for (Tuple tuple : myzset) {
            logger.info(tuple.getScore() + " : " + tuple.getElement());
        }

        // del
        logger.info("del myzset : " + jedis.del("myzset"));
    }
}
