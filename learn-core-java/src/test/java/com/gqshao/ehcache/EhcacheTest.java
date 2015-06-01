package com.gqshao.ehcache;

import com.google.common.collect.Sets;
import com.gqshao.file.FileTest;
import com.gqshao.file.util.FileUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EhcacheTest {

    private static final Logger logger = LoggerFactory.getLogger(EhcacheTest.class);

    /**
     * 通过HashSet去重
     */
    @Test
    public void removeRepeatBySet() {
        long begin = System.currentTimeMillis(), start = System.currentTimeMillis(), end;
        logger.info(String.valueOf(FileTest.file.exists()));
        Set<String> set = Sets.newHashSet();

        long pos = 0L;
        int i = 0, j = 0, total = 0;
        while (true) {
            Map<String, Object> res = FileUtil.BufferedRandomAccessFileReadLine(FileTest.file, FileTest.ENCODING, pos, FileTest.NUM);
            Set<String> pins = (Set<String>) res.get("pins");
            if (CollectionUtils.isNotEmpty(pins)) {
                for (String pin : pins) {
                    if (!set.contains(pin)) {
                        i++;
                        set.add(pin);
                    } else {
                        j++;
                    }
                }
                pos = (Long) res.get("pos");
                total += pins.size();
                end = System.currentTimeMillis();
                logger.info("过滤总数" + total + "万, 不重复" + i + "个,重复" + j + "个, set size" + set.size() + "用时 " + (end - start) + "毫秒");
                start = end;
            } else {
                break;
            }
        }
        logger.info("总用时 " + ((System.currentTimeMillis() - begin) / 1000) + "秒");
    }

    /**
     * 通过HashSet去重 超200w效率极低
     */
    @Test
    public void removeRepeatByEhCache() {
        long begin = System.currentTimeMillis(), start = System.currentTimeMillis(), end;
        logger.info(String.valueOf(FileTest.file.exists()));
        Cache cache = null;
        try {
            cache = EhCacheService.getCache();
            long pos = 0L;
            int i = 0, j = 0, total = 0;
            while (true) {
                Map<String, Object> res = FileUtil.BufferedRandomAccessFileReadLine(FileTest.file, FileTest.ENCODING, pos, FileTest.NUM);
                List<String> pins = (List<String>) res.get("pins");
                if (CollectionUtils.isNotEmpty(pins)) {
                    for (String pin : pins) {
                        Element element = cache.get(pin);
                        if (null == element) {
                            i++;
                            cache.put(new Element(pin, null));
                        } else {
                            j++;
                        }
                    }
                    pos = (Long) res.get("pos");
                    total += pins.size();
                    end = System.currentTimeMillis();
                    logger.info("过滤总数" + total + "万, 不重复" + i + "个,重复" + j + "个, set size" + cache.getSize() + "用时 " + (end - start) + "毫秒");
                    logger.info("cache size " + cache.getSize());
                    start = end;
                    if (pins.size() < FileTest.NUM) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EhCacheService.shutdown();
        }

        logger.info("总用时 " + ((System.currentTimeMillis() - begin) / 1000) + "秒");
    }


    @Test
    public void shutdown() {
        EhCacheService.shutdown();
    }
}
