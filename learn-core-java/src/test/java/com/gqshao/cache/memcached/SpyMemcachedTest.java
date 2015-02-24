package com.gqshao.cache.memcached;

import net.spy.memcached.MemcachedClient;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class SpyMemcachedTest {

    @Test
    public void test() {
        try {
            MemcachedClient client = new MemcachedClient(new InetSocketAddress("192.168.3.98", 11211));
            List<String> someObject = new ArrayList<String>();
            someObject.add("value1");
            someObject.add("value2");
            someObject.add("value3");
            // Store a value (async) for one hour
            client.set("someKey", 1000, someObject);
            Thread.sleep(2000);
            // Retrieve a value.
            Object myObject = client.get("someKey");
            if (myObject instanceof ArrayList) {
                System.out.println("result is list");
                List<String> resList = (ArrayList<String>) myObject;
                for (String res : resList) {
                    System.out.println(res);
                }
            } else if (myObject == null) {
                System.out.println("result is null");
            } else {
                System.out.println("error");
            }

            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}