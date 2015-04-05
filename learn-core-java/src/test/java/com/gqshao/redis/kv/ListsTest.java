package com.gqshao.redis.kv;

import com.gqshao.redis.JedisTestBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryClient;

/**
 * Lists 是一个链表结构主要功能是push、pop等
 * Redis的List是双向链表，最大长度是2的32次方（4G）
 * index(下标) 正序(从左到右，从上到下)从0开始,逆序(从右到左，从下至上)从-1开始
 */
public class ListsTest extends JedisTestBase {

    protected static Logger logger = LoggerFactory.getLogger(ListsTest.class);

    /**
     * lpush [key] [value] 在key对应的list头部(左侧或上侧)添加Strings元素,返回Lists长度,可一次输入多个value，按输入顺序添加依次到头部
     * rpush [key] [value]  在key对应的list尾部(右侧或下侧)添加Strings元素,返回Lists长度,可一次输入多个value，按输入顺序添加到尾部
     * linsert [key] [index] [before|after] 在key对应的list指定value(value可能重复，在从头开始遇到的第一个生效)的前或后添加
     * lset [key] [index] [value] 通过指定下标修改value，注意下标不能超出已有范围
     * lrange [key] [start] [end] 返回指定下标范围内的元素(下标从0开始,尾部从-1开始)
     */
    @Test
    public void testPush() {
        // lpush
        logger.info("test lpush");
        logger.info("lpush mylist world : " + jedis.lpush("mylist", "world"));
        logger.info("lpush mylist hello : " + jedis.lpush("mylist", "hello"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");
        logger.info("lpush mylist a b c d : " + jedis.lpush("mylist", "a", "b", "c", "d"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");
        // rpush
        logger.info("test rpush");
        logger.info("rpush mylist 0 : " + jedis.rpush("mylist", "0"));
        logger.info("rpush mylist 1 2 3 4 : " + jedis.rpush("mylist", "1", "2", "3", "4"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");

        // linsert
        logger.info("test linsert");
        logger.info("linsert mylist 1 after 1 : " + jedis.linsert("mylist", BinaryClient.LIST_POSITION.AFTER, "1", "1"));
        logger.info("linsert mylist 3 before 3 : " + jedis.linsert("mylist", BinaryClient.LIST_POSITION.BEFORE, "3", "3"));
        logger.info("linsert mylist 1 after after_1 : " + jedis.linsert("mylist", BinaryClient.LIST_POSITION.AFTER, "1", "after_1"));
        logger.info("linsert mylist 3 before before_3 : " + jedis.linsert("mylist", BinaryClient.LIST_POSITION.BEFORE, "3", "before_3"));
        logger.info("linsert mylist 3 after after_3 : " + jedis.linsert("mylist", BinaryClient.LIST_POSITION.AFTER, "3", "after_3"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");

        // del
        logger.info("del mylist : " + jedis.del("mylist") + "\n");

        // lset
        logger.info("test lset");
        logger.info("lpush mylist a b c d : " + jedis.lpush("mylist", "a", "b", "c", "d"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");
        logger.info("lset mylist 0 zero : " + jedis.lset("mylist", 0, "zero"));
        logger.info("lset mylist -2 three : " + jedis.lset("mylist", -2, "three"));
        try {
            logger.info("lset mylist 4 four : " + jedis.lset("mylist", 4, "four"));
        } catch (Exception e) {
            logger.warn("lset修改值不能超过Lists目前长度", e);
        }
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");

        // del
        logger.info("del mylist : " + jedis.del("mylist") + "\n");
    }

    /**
     * lrem [key] [count] [value] 从key所对应的Lists中删除count个指定value
     * count>0时，从头到尾删除count个value
     * count<0时，从尾到头删除count个value
     * count=0时，全部删除
     */
    @Test
    public void testLrem() {
        // count>0
        logger.info("rpush mylist foo foo bar foo foo : " + jedis.rpush("mylist", "foo", "foo", "bar", "foo", "foo"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");
        logger.info("lrem mylist 2 foo : " + jedis.lrem("mylist", 2, "foo"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");

        // count<0
        logger.info("lrem mylist -1 foo : " + jedis.lrem("mylist", -1, "foo"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1));
        // del
        logger.info("del mylist : " + jedis.del("mylist") + "\n");

        // count=0
        logger.info("rpush mylist foo foo bar foo foo : " + jedis.rpush("mylist", "foo", "foo", "bar", "foo", "foo"));
        logger.info("lrem mylist 0 foo : " + jedis.lrem("mylist", 0, "foo"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");
        // del
        logger.info("del mylist : " + jedis.del("mylist") + "\n");
    }

    /**
     * ltrim [key] [start] [end] 保留list中指定范围内的value(头从0开始，尾从-1开始)
     * 如果start在end之后，相当于清空Lists
     */
    @Test
    public void testLtrim() {
        logger.info("rpush mylist 0 1 2 3 4 : " + jedis.rpush("mylist", "0", "1", "2", "3", "4"));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");

        logger.info("保留最大范围，没有数据被移除");
        logger.info("ltrim mylist 0 -1 : " + jedis.ltrim("mylist", 0, -1));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");

        logger.info("保留1到-2，头尾各删除一个");
        logger.info("ltrim mylist 1 -2 : " + jedis.ltrim("mylist", 1, -2));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");

        logger.info("保留1到1，保留的结果等于 lindex mylist 1 取到的值");
        logger.info("lindex mylist 1 : " + jedis.lindex("mylist", 1));
        logger.info("ltrim mylist 1 1 : " + jedis.ltrim("mylist", 1, 1));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");
        logger.info("del mylist : " + jedis.del("mylist") + "\n");

        // start > end
        logger.info("如果start在end之后，相当于全删");
        logger.info("rpush mylist 0 1 2 3 4 : " + jedis.rpush("mylist", "0", "1", "2", "3", "4"));
        logger.info("ltrim mylist 1 0 : " + jedis.ltrim("mylist", 1, 0));
        logger.info("lrange mylist 0 -1 : " + jedis.lrange("mylist", 0, -1) + "\n");

        // del
        logger.info("del mylist : " + jedis.del("mylist") + "\n");
    }

    /**
     * lpop [key] 从list头部移出并返回一个元素
     * rpop [key] 从list尾部移出并返回一个元素
     * rpoplpush [list1] [list2] 从第一个list1尾部移出一个元素，添加到list2头部，并返回该元素,rpoplpush是一个原子操作
     */
    @Test
    public void testPop() {

        logger.info("rpush mylist 0 1 2 3 4 : " + jedis.rpush("mylist", "0", "1", "2", "3", "4") + "\n");

        // lpop
        logger.info("test lpop");
        logger.info("lpop mylist : " + jedis.lpop("mylist") + "\n");

        // rpop
        logger.info("test rpop");
        logger.info("rpop mylist : " + jedis.rpop("mylist"));
        logger.info("del mylist : " + jedis.del("mylist") + "\n");


        // rpoplpush
        logger.info("test rpoplpush");
        logger.info("rpush mylist1 0 1 2 3 4 : " + jedis.rpush("mylist1", "0", "1", "2", "3", "4"));
        logger.info("rpush mylist2 zero one two : " + jedis.rpush("mylist2", "zero", "one", "two"));
        logger.info("rpoplpush mylist1 mylist2 : " + jedis.rpoplpush("mylist1", "mylist2"));
        logger.info("lrange mylist1 0 -1 : " + jedis.lrange("mylist1", 0, -1));
        logger.info("lrange mylist2 0 -1 : " + jedis.lrange("mylist2", 0, -1));
        logger.info("del mylist1 mylist2: " + jedis.del("mylist1", "mylist2"));

    }

    /**
     * lindex [key] [index] 返回Lists指定下标的value
     * llen [key] 返回Lists的长度
     */
    @Test
    public void testLindexAndLlen() {
        // lindex
        logger.info("lindex");
        logger.info("rpush mylist 0 1 2 3 4 : " + jedis.rpush("mylist", "0", "1", "2", "3", "4"));
        logger.info("lindex mylist 3 : " + jedis.lindex("mylist", 3));
        logger.info("lindex mylist -3 : " + jedis.lindex("mylist", -3) + "\n");

        // llen
        logger.info("test llen");
        logger.info("llen mylist : " + jedis.llen("mylist"));

        // del
        logger.info("del mylist : " + jedis.del("mylist"));
    }
}
