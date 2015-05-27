package com.gqshao.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.gqshao.concurrency.service.ThreadService;
import com.gqshao.concurrency.utils.MyUncaughtExceptionHandler;
import com.gqshao.concurrency.utils.ThreadUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ThreadTest {

    private static final Logger logger = LoggerFactory.getLogger(ThreadTest.class);

    private static Thread.UncaughtExceptionHandler handler;

    @BeforeClass
    public static void init() {
        handler = new MyUncaughtExceptionHandler();
    }

    /**
     * 测试固定100个线程的调用
     */
    @Test
    public void testCommonsLang3ThreadFactory() {
        // 设置递减锁存器阻塞main线程
        CountDownLatch threadSignal = new CountDownLatch(100);
        BasicThreadFactory factory = ThreadUtil.getThreadFactoryByCommonsLang3("commons-lang3-factory-thread-%d", handler);
        ExecutorService pool = Executors.newFixedThreadPool(100, factory);
        for (int i = 0; i < 100; i++) {
            pool.execute(new ThreadService(threadSignal));
        }
        try {
            threadSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("commons-lang3-ThreadFactory test over");
    }

    /**
     * 错误用例，未捕获异常处理器不能捕捉处理Exception
     */
    @Test
    public void testGuavaThreadFactory1() throws InterruptedException {
        ThreadFactory factory = ThreadUtil.getThreadFactoryByGuava("guava-factory-thread-%d", handler);
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(factory);
        scheduledExecutor.scheduleWithFixedDelay(new ThreadService(), 0, 1, TimeUnit.SECONDS);
        Thread.sleep(5000);
    }

    /**
     * 在执行的Runnable实现类上套一层，防止抛出异常导致中断了线程池中的线程
     * <p>
     *     备选方案:
     *     Spring TaskUtils的LOG_AND_SUPPRESS_ERROR_HANDLER
     *     Runnable task = TaskUtils.decorateTaskWithErrorHandler(new Runnable(){...}, null, true);
     * </p>
     *
     */
    @Test
    public void testGuavaThreadFactory2() throws InterruptedException {
        ThreadFactory factory = ThreadUtil.getThreadFactoryByGuava("guava-factory-thread-%d", handler);
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(factory);
        scheduledExecutor.scheduleWithFixedDelay(new ThreadUtil.WrapExceptionRunnable(new ThreadService()), 0, 1, TimeUnit.SECONDS);
        Thread.sleep(10000);
        logger.info("ThreadUtil gracefulShutdown begin");
        // 关闭线程池 在此之后不会有线程日志
        ThreadUtil.gracefulShutdown(scheduledExecutor,1000,1000,TimeUnit.MILLISECONDS);
        logger.info("ThreadUtil gracefulShutdown over");
        Thread.sleep(5000);
        logger.info("no message");
    }


}
