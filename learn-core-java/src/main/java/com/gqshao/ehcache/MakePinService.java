package com.gqshao.ehcache;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class MakePinService {

    public static void main(String[] args) {
        MakePinService service = new MakePinService();

        service.makePin();
        service.deduplicationPinByHashSet();
        service.deduplicationPinByEhCache();
    }

    /**
     * 生成随机数字
     */
    public void makePin() {
        String desktop = "C:\\Users\\shaogq\\Desktop\\test\\";
        String prefix = "_$#";
        for (int i = 0; i < 10; i++) {
            PrintWriter out = null;
            try {
                File file = new File(desktop + i + ".txt");
                out = new PrintWriter(file);
                for (int j = 0; j < 2000000; j++) {
                    out.println(prefix + (int) (19000000 * Math.random()));
                }
                out.flush();
            } catch (Exception e) {

            } finally {
                IOUtils.closeQuietly(out);
            }
        }
    }

    /**
     * 通过HashSet去重
     */
    public void deduplicationPinByHashSet() {
        long first = DateTime.now().getMillis();
        Long end = null;
        Set<String> set = Sets.newHashSet();
        for (int i = 0; i < 400; i++) {
            Long start = DateTime.now().getMillis();
            List<String> pins = this.readPin(i);
            if (CollectionUtils.isEmpty(pins)) {
                break;
            }
            List<String> newList = Lists.newLinkedList();
            int j = 0, k = 0;
            for (String pin : pins) {
                if (!set.contains(pin)) {
                    j++;
                    set.add(pin);
                } else {
                    k++;
                }
            }
            end = DateTime.now().getMillis();
            System.out.println("过滤总数" + ((i + 1) * 5) + "万, 本次不重复" + j + ",重复" + k + ", set size" + set.size() + "用时 " + (end - start));
        }
        if (end != null) {
            System.out.println("总用时" + ((end - first) / 1000));
        }


    }

    /**
     * 通过EhCache去重
     */
    public void deduplicationPinByEhCache() {
        long first = DateTime.now().getMillis();
        Long end = null;

        URL url = ClassLoader.getSystemResource("ehcache.xml");
        CacheManager cacheManager = CacheManager.create(url);
        String[] cacheNames = cacheManager.getCacheNames();
        Cache cache = cacheManager.getCache("pinCache");

        for (int i = 0; i < 400; i++) {
            Long start = DateTime.now().getMillis();
            List<String> pins = this.readPin(i);
            if (CollectionUtils.isEmpty(pins)) {
                break;
            }

            int j = 0, k = 0;
            for (String pin : pins) {
                Element element = cache.get(pin);
                if (element == null) {
                    j++;
                    cache.put(new Element(pin, null));
                } else {
                    k++;
                }
            }
            end = DateTime.now().getMillis();
            System.out.println("过滤总数" + ((i + 1) * 5) + "万, 本次不重复" + j + ",重复" + k + ", 用时 " + (end - start));
            if (((i + 1) * 5) == 1000) {
                System.out.println("1000万总用时" + ((end - first) / 1000));
            }
        }
        if (end != null) {
            System.out.println("总用时" + ((end - first) / 1000));
        }
    }

    /**
     * index文件序号从0开始
     *
     * @param index
     * @return
     */
    public List<String> readPin(int index) {
        File file = new File("C:\\Users\\shaogq\\Desktop\\test\\" + index + ".txt");
        List<String> list = Lists.newLinkedList();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            int i = 0;
            while ((line = in.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        return list;
    }


}
