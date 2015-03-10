package com.gqshao.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * JDK7 NIO2 文件监控
 */
public class MyWatcherService {
    public static void main(String[] args) throws IOException, InterruptedException {
        String dir = "C:\\Users\\Administrator\\Desktop\\test";
        Path path = Paths.get(dir);
        WatchService watcher = FileSystems.getDefault().newWatchService();
        path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        while (true) {
            /**
             * 拷贝文件会触发 一次ENTRY_CREATE 两次ENTRY_MODIFY(文件可能只触发一次ENTRY_MODIFY)
             * 拷贝文件会触发 一次ENTRY_CREATE 两次ENTRY_MODIFY 一次ENTRY_DELETE
             * 删除文件会触发 一次ENTRY_DELETE
             */
            WatchKey key = watcher.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {//事件可能lost or discarded
                    System.out.println("事件可能lost or discarded");
                    continue;
                }

                WatchEvent<Path> e = (WatchEvent<Path>) event;
                String kindName = kind.name();
                Path fileName = e.context();

                System.out.printf("Event %s has happened,which fileName is %s%n", kindName, fileName);

                if ("ENTRY_MODIFY".equals(kindName)) {
                    rename(dir, fileName.toString());
                }
            }
            // 重置 key 如果失败结束监控
            if (!key.reset()) {
                break;
            }
        }
    }

    /**
     * 通过修改文件名判断文件拷贝完成
     */
    public static void rename(String dir, String fileName) {
        File file = new File(dir + File.separator + fileName);
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\test2" + File.separator + fileName);
        file.renameTo(file2);
        if (file2.exists()) {
            System.out.printf("file %s copy over , you can do next with %s%n", file.getAbsolutePath(), file2.getAbsolutePath());
        }
    }
}