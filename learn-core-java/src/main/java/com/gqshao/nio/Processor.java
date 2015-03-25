package com.gqshao.nio;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * <pre>
 * 功能说明：通过移动文件的方法判断文件拷贝完成 并处理文件
 * </pre>
 */
public class Processor implements Runnable {

    // 时间格式化Format
    private DateTimeFormatter dirNameFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH mm ss ");

    private BlockingQueue<String> queue;

    public Processor(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String sourceDir = "D:\\bigdata\\src";
        String resDir = "D:\\bigdata\\res";
        try {
            while (true) {
                String fileName = queue.take();
                File src = new File(sourceDir + File.separator + fileName);
                // 再一次移动防止提交重名文件覆盖正在处理的文件
                File dest = new File(resDir + File.separator
                        + DateTime.now().toString(dirNameFormat)
                        + UUID.randomUUID().toString().replaceAll("-", "")
                        + File.separator + fileName);
                try {
                    // 未拷贝完成不能移动
                    FileUtils.moveFile(src, dest);
                } catch (Exception e) {
                    // 删除建立的空目录
                    FileUtils.deleteQuietly(dest.getParentFile());
                }

                if (dest.exists()) {
                    try {
                        System.out.printf("file %s copy over , you can do next with %s%n", src.getAbsolutePath(), dest.getAbsolutePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("处理异常, 停止程序");
            // 无法处理QAR数据 结束程序
            System.exit(1);
        }
    }
}