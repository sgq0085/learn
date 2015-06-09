package com.gqshao.h2;

import com.gqshao.file.FileTest;
import com.gqshao.file.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class H2Test {

    private static final Logger logger = LoggerFactory.getLogger(H2Test.class);

    @Test
    public void create() {
        H2Service.create();
    }


    /**
     * 通过H2去重
     */
    @Test
    public void removeRepeatByH2() {
        long begin = System.currentTimeMillis(), start = System.currentTimeMillis(), end;
        logger.info(String.valueOf(FileTest.file.exists()));

        long pos = 0L;
        int i, j, total = 0;
        while (true) {
            Map<String, Object> res = FileUtil.BufferedRandomAccessFileReadLine(FileTest.file, FileTest.ENCODING, pos, FileTest.NUM);
            Set<String> pins = (Set<String>) res.get("pins");
            if (CollectionUtils.isNotEmpty(pins)) {
                pos = (Long) res.get("pos");
                int tmpSize = pins.size();
                total += tmpSize;
                H2Service.removeRepeat(pins);
                i = pins.size();
                j = tmpSize - i;
                end = System.currentTimeMillis();

                logger.info("过滤总数" + total + ", 本次处理数据量" + tmpSize + ", 不重复" + i + "个,重复" + j + "个 用时 " + (end - start) + "毫秒");
                start = end;
            } else {
                break;
            }
        }
        logger.info("总用时 " + ((System.currentTimeMillis() - begin) / 1000) + "秒");
    }
}
