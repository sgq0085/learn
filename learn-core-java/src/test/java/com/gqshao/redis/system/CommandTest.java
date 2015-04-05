package com.gqshao.redis.system;

import com.gqshao.redis.JedisTestBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 常用命令测试
 */
public class CommandTest extends JedisTestBase {

    protected static Logger logger = LoggerFactory.getLogger(CommandTest.class);

    /**
     * keys [patten] 返回满足给定patten的所有key
     * dbsize 统计key的数量
     * exists [key] 确认一个key是否存在，返回boolean
     * del [key...] 删除一个key或多个key
     * expire [key] [seconds] 设置一个key的过期时间(单位:秒),可多次使用来更新过期时间
     * ttl [key] 检查过期时间，返回值是秒
     * persist [key] 移除给定key的过期时间
     * randomkey 随机返回一个key
     * rename [oldKey] [newKey] 重命名key,最好先用exists检测一下，不存在会抛出JedisDataException
     * type [key] value的类型
     */
    @Test
    public void testKeys() {
        // keys
        logger.info("test keys");
        logger.info("mset keym1 value1 keym2 value2 keym3 value3 : " + jedis.mset("keym1", "value1", "keym2", "value2", "keym3", "value3"));
        logger.info("keys keym* : " + jedis.keys("keym*") + "\n");

        // dbsize
        logger.info("test dbsize");
        logger.info("dbsize : "+jedis.dbSize()+"\n");

        // exists
        logger.info("test exists");
        logger.info("exists keym1 : " + jedis.exists("keym1"));
        logger.info("exists keym4 : " + jedis.exists("keym4") + "\n");

        // expire
        logger.info("test expire");
        logger.info("expire keym3 1 : " + jedis.expire("keym3", 1));
        logger.info("ttl keym3 : " + jedis.ttl("keym3"));
        logger.info("sleep 1500");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("ttl keym3 : " + jedis.ttl("keym3") + "\n");

        // persist
        logger.info("test persist");
        logger.info("expire keym2 10 : " + jedis.expire("keym2", 10));
        logger.info("ttl keym2 : " + jedis.ttl("keym2"));
        logger.info("persist keym2 : " + jedis.persist("keym2"));
        logger.info("ttl keym2 : " + jedis.ttl("keym2") + "\n");

        // randomkey
        logger.info("test randomkey");
        logger.info("randomkey : " + jedis.randomKey() + "\n");

        // rename
        logger.info("test randomkey");
        logger.info("rename keym2 keynew2 : " + jedis.rename("keym2", "keynew2"));
        logger.info("get keynew2 : " + jedis.get("keynew2") + "\n");

        // type
        logger.info("test type");
        logger.info("type keym1 : " + jedis.type("keym1"));

        logger.info("del keym1 keym2 keynew2 keym3 : " + jedis.del("keym1", "keym2", "keynew2", "keym3"));
    }

    /**
     * select [dbIndex] 显示选择数据库
     * move [key] [dbIndex] 将当前数据库中的key转移到指定数据库中
     * flushdb 清空当前数据库
     * flushall 清空所有数据库
     * dbsize 返回当前数据库里面的keys数量
     */
    @Test
    public void testDB() {
        // 慎用
        // jedis.flushDB();
        // jedis.flushAll();

        // select
        // 显示使用数据库0
        logger.info("select 0 : " + jedis.select(0));
        // move
        logger.info("设置kv");
        logger.info("set mykey value : " + jedis.set("mykey", "foo"));
        logger.info("get mykey : " + jedis.get("mykey"));

        logger.info("将mykey移动到数据库1");
        logger.info("move mykey 1 : " + jedis.move("mykey", 1));

        logger.info("数据库0中查询不到");
        logger.info("get mykey : " + jedis.get("mykey"));

        logger.info("使用数据库1");
        logger.info("select 1 : " + jedis.select(1));
        logger.info("get mykey : " + jedis.get("mykey") + "\n");

        // dbsize
        logger.info("test dbsize");
        logger.info("dbsize : " + jedis.dbSize());

        // del
        logger.info("del mykey : " + jedis.del("mykey"));

    }

}
