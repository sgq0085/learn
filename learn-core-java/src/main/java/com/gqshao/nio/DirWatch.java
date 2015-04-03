package com.gqshao.nio;

import java.nio.file.*;
import java.util.concurrent.BlockingQueue;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * <pre>
 * 功能说明：通过NIO 监控文件夹并放入队列中
 * </pre>
 */
public class DirWatch implements Runnable {

    private BlockingQueue<String> queue;

    public DirWatch(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String sourceDir = "D:\\bigdata\\src";

        try {

            // 对文件夹进行监控
            Path path = Paths.get(sourceDir);
            WatchService watcher = FileSystems.getDefault().newWatchService();
            path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            while (true) {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind kind = event.kind();

                    if (kind == OVERFLOW) {
                        System.out.println("事件可能lost or discarded");
                        continue;
                    }

                    WatchEvent<Path> e = (WatchEvent<Path>) event;
                    String kindName = kind.name();
                    Path fileName = e.context();

//                    System.out.printf("Event %s has happened,which fileName is %s%n", kindName, fileName);

                    if ("ENTRY_MODIFY".equals(kindName)) {
                        // 放到队列中 如果队列满抛出异常 证明程序逻辑出现问题 理论上不可能满
                        queue.add(fileName.toString());
                    }
                }
                // 重置 key 如果失败结束监控
                if (!key.reset()) {
                    throw new RuntimeException("重置Key失败，结束监控");
//                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("监控异常, 停止程序");
            // 无法处理QAR数据 结束程序
            System.exit(1);
        }
    }
}