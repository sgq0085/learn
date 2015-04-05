package com.gqshao.redis.system;

import com.gqshao.redis.JedisTestBase;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;

public class ServerTest extends JedisTestBase {

    protected static Logger logger = LoggerFactory.getLogger(ServerTest.class);

    /**
     * quit 退出连接，只对当前得到的连接有影响，不影响pool中连接
     */
    @Test
    public void testQuit() {
        // quit
        logger.info("test quit");
        logger.info("quit : " + jedis.quit());
        try {
            logger.info("ping : " + jedis.ping() + "\n");
        } catch (Exception e) {
            logger.warn("已经退出连接");
            e.printStackTrace();
        }
    }

    /**
     * ping 测试连接是否存活
     * echo 打印一些内容
     * dbsize 统计key的数量
     * info 获取服务器的信息和统计
     * config get [parameter] 获取服务器配置信息
     */
    @Test
    public void test() {
        // ping
        logger.info("test ping");
        logger.info("ping : " + jedis.ping() + "\n");

        // echo
        logger.info("test echo");
        logger.info("echo \"Hello World!\"" + jedis.echo("Hello World!") + "\n");

        // dbsize
        logger.info("test dbsize");
        logger.info("dbsize : " + jedis.dbSize());

        // info
        logger.info("test info");
        logger.info("info" + jedis.info() + "\n");

        // config get
        logger.info("config get test");
        logger.info("config get dir" + jedis.configGet("dir"));
        logger.info("config get" + jedis.configGet("*"));
    }

    /**
     * monitor 实时监控服务器
     */
    @Ignore
    @Test
    public void testMonitor() {
        // 开启监控线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis monitorJedis = null;
                final long start = DateTime.now().getMillis();
                try {
                    monitorJedis = pool.getResource();
                    jedis.monitor(new JedisMonitor() {
                        public void onCommand(String command) {
                            // 开始10秒之后结束
                            if (DateTime.now().minus(start).getMillis() > 10000) {
                                System.exit(0);
                            }
                            System.out.println(command);
                        }
                    });
                } finally {
                    monitorJedis.close();
                }
            }
        }).start();

        // 开启监控线程
        new Thread(new Runnable() {
            public void run() {
                Jedis pingJedis = null;
                try {
                    pingJedis = pool.getResource();
                    for (int i = 0; i < 100; i++) {
                        try {
                            pingJedis.ping();
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                } finally {
                    pingJedis.close();
                }

            }
        }).start();


        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
