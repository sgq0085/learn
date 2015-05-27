package com.gqshao.concurrency.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.*;

public class ThreadUtil {
    /**
     * 通过Guava构造ThreadFactory
     *
     * @param pattern 线程名,序号为%d
     * @param handler 未捕获异常处理器
     * @return ThreadFactory
     */
    public static BasicThreadFactory getThreadFactoryByCommonsLang3(String pattern, Thread.UncaughtExceptionHandler handler) {
        BasicThreadFactory.Builder builder = new BasicThreadFactory.Builder();
        if (StringUtils.isNotBlank(pattern)) {
            builder.namingPattern(pattern);
        }
        if (handler != null) {
            builder.uncaughtExceptionHandler(handler);
        }
        return builder.build();
    }

    public static BasicThreadFactory getThreadFactoryByCommonsLang3(String pattern) {
        return getThreadFactoryByCommonsLang3(pattern, null);
    }


    /**
     * 通过Guava构造ThreadFactory
     *
     * @param format  线程名,序号为%d
     * @param handler 未捕获异常处理器
     * @return ThreadFactory
     */
    public static ThreadFactory getThreadFactoryByGuava(String format, Thread.UncaughtExceptionHandler handler) {
        ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
        if (StringUtils.isNotBlank(format)) {
            builder.setNameFormat(format);
        }
        if (handler != null) {
            builder.setUncaughtExceptionHandler(handler);
        }
        return builder.build();
    }

    public static ThreadFactory getThreadFactoryByGuava(String format) {
        return getThreadFactoryByGuava(format, null);
    }

    /**
     * 按照ExecutorService JavaDoc示例代码编写的Graceful Shutdown方法.
     * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
     * 如果超时, 则调用shutdownNow, 取消在workQueue中Pending的任务,并中断所有阻塞函数.
     * 如果仍然超時，則強制退出.
     * 另对在shutdown时线程本身被调用中断做了处理.
     */
    public static void gracefulShutdown(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout,
                                        TimeUnit timeUnit) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
                    System.err.println("Pool did not terminated");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 直接调用shutdownNow的方法, 有timeout控制.取消在workQueue中Pending的任务,并中断所有阻塞函数.
     */
    public static void normalShutdown(ExecutorService pool, int timeout, TimeUnit timeUnit) {
        try {
            pool.shutdownNow();
            if (!pool.awaitTermination(timeout, timeUnit)) {
                System.err.println("Pool did not terminated");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 保证不会有Exception抛出到线程池的Runnable，防止用户没有捕捉异常导致中断了线程池中的线程。
     */
    public static class WrapExceptionRunnable implements Runnable {

        private static Logger logger = LoggerFactory.getLogger(WrapExceptionRunnable.class);

        private Runnable runnable;

        public WrapExceptionRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                runnable.run();
            } catch (Throwable e) {
                StringWriter stringWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(stringWriter));
                logger.error("线程{}出错, 异常信息如下{}", Thread.currentThread().getName(), stringWriter.toString());
            }
        }
    }

}
