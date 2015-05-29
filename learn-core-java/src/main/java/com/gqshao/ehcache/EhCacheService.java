package com.gqshao.ehcache;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EhCacheService {

    private static final Logger logger = LoggerFactory.getLogger(EhCacheService.class);

    private static URL url = ClassLoader.getSystemResource("ehcache.xml");
    private static CacheManager cacheManager;

    public static Cache getCache() {

        cacheManager = CacheManager.create(url);
        String[] cacheNames = cacheManager.getCacheNames();
        logger.info(Arrays.toString(cacheNames));
        return cacheManager.getCache("pinCache");
    }

    public static void removeAll() {
        cacheManager.removeCache("pinCache");
    }

    public static void shutdown() {
        if (cacheManager == null) {
            cacheManager = CacheManager.create(url);
        }
        cacheManager.shutdown();
    }

}
