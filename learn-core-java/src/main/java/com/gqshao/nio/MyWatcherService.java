package com.gqshao.nio;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * JDK7 NIO2 文件监控
 */
public class MyWatcherService {

    // 时间格式化Format
    private static final DateTimeFormatter dirNameFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH mm ss ");

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
        File src = new File(dir + File.separator + fileName);
        String tmp = "C:\\Users\\Administrator\\Desktop\\test2";
        File dest = new File(tmp + File.separator
                + DateTime.now().toString(dirNameFormat)
                + UUID.randomUUID().toString().replaceAll("-", "")
                + File.separator + fileName);
        try {
            // 未拷贝完成不能移动
            FileUtils.moveFile(src, dest);
        } catch (Exception e) {
            FileUtils.deleteQuietly(dest.getParentFile());
        }
        if (dest.exists()) {
            System.out.printf("file %s copy over , you can do next with %s%n", src.getAbsolutePath(), dest.getAbsolutePath());
        }
    }
}