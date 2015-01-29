package com.gqshao.redis.system;

import com.gqshao.redis.JedisTest;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisMonitor;

public class ServerTest extends JedisTest {

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
    }

    /**
     * monitor 实时监控服务器
     */
    @Ignore
    @Test
    public void testMonitor() {
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    jedis.ping();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }
                jedis.disconnect();
            }
        }).start();

        jedis.monitor(new JedisMonitor() {
            public void onCommand(String command) {
                System.out.println(command);
            }
        });
    }


}
