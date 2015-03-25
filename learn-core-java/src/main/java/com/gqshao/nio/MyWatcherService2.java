package com.gqshao.nio;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <pre>
 * 功能说明：启动两个线程一个监控文件夹改变 一个处理文件
 * </pre>
 */
public class MyWatcherService2 {
    public static void main(String[] args) {

        // 创建单向队列queue DirWatch进程将监控到的文件放到队列中 Processor检查处理队列中的文件
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

        DirWatch watch = new DirWatch(queue);
        Thread watchThread = new Thread(watch);
        watchThread.start();

        Processor processor = new Processor(queue);
        Thread processorThread = new Thread(processor);
        processorThread.start();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}