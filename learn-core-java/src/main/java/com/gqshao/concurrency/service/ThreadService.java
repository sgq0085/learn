package com.gqshao.concurrency.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ThreadService implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ThreadService.class);
    private CountDownLatch threadSignal;

    public ThreadService() {

    }

    public ThreadService(CountDownLatch threadSignal) {
        this.threadSignal = threadSignal;
    }

    @Override
    public void run() {
        logger.info(Thread.currentThread().getName());
        // 如果有同步器 递减锁存器的计数
        if (threadSignal != null) {
            threadSignal.countDown();
        }
        throw new RuntimeException("线程抛出异常信息");
    }
}
