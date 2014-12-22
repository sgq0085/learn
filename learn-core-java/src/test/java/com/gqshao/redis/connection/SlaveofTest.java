package com.gqshao.redis.connection;


import com.gqshao.redis.utils.JedisUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * slaveof 和 slaveofNoOne方法可以在运行中动态设置服务器为Master或Slave
 */
public class SlaveofTest {

    protected static Logger logger = LoggerFactory.getLogger(SlaveofTest.class);

    private static JedisPool pool1 = null;
    protected Jedis jedis1 = null;

    private static JedisPool pool2 = null;
    protected Jedis jedis2 = null;


    @BeforeClass
    public static void init() {
        JedisPoolConfig poolConfig = JedisUtils.getMyDefaultJedisPoolConfig();
        // timeout设置为0，解决JedisConnectionException
        pool1 = new JedisPool(poolConfig, "192.168.3.98", 6379, 0);
        pool2 = new JedisPool(poolConfig, "192.168.3.98", 6380, 0);
        logger.info("----------------------create resource----------------------");
    }

    @AfterClass
    public static void destroy() {
        pool1.destroy();
        pool2.destroy();
    }

    @Before
    public void initJedis() {
        jedis1 = pool1.getResource();
        jedis2 = pool2.getResource();
    }

    @After
    public void destroyJedis() {
        pool1.returnResource(jedis1);
        pool2.returnResource(jedis2);
    }

    /**
     * 测试主从复制 注意设置Slave之后需要时间复制数据
     */
    @Test
    public void test() {
        // 设置 Redis2为Redis1的slave
        jedis1.slaveofNoOne();
        jedis2.slaveof("192.168.3.98", 6379);

        try {
            jedis2.set("key", "value");
        } catch (Exception e) {
            logger.warn("由于设置的原因slave为read only");
        }

        // 主从复制 Redis1中保存数据Redis2中可以读取
        logger.info("jedis1.set mykey1 myvalue1 : " + jedis1.set("mykey1", "myvalue1"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("jedis2.get mykey1: " + jedis2.get("mykey1"));

        // 单点问题解决思路(思路启用Redis2为Master，Redis1为SlaveOf Redis1)
        // 提升Redis2为Master，读取刚才的数据，重启Redis1，将Redis2设置为Redis1的Master
        jedis2.slaveofNoOne();
        // 测试值仍然存在
        logger.info("jedis2.get again : " + jedis2.get("mykey1"));
        // Redis2中存入数据
        logger.info("jedis2.set mykey2 myvalue2 : "+jedis2.set("mykey2", "myvalue2"));
        // Redis1设置为Redis2的slave
        jedis1.slaveof("192.168.3.98", 6380);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 测试jedis1中读取数据
        logger.info("jedis1.get mykey2 : " + jedis1.get("mykey2"));

        jedis1.slaveofNoOne();
        jedis1.del("mykey1", "mykey2");
        jedis2.del("mykey1", "mykey2");
    }

}
