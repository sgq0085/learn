package com.gqshao.memcached;

import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class TestMem {

public static void main(String[] args) {
    try {
        MemcachedClient client = new MemcachedClient(new InetSocketAddress("192.168.158.131", 11211));
        Object someObject = new ArrayList<String>();
        // Store a value (async) for one hour
        client.set("someKey", 3600, someObject);
        // Retrieve a value.
        Object myObject = client.get("someKey");
        System.out.println(myObject instanceof ArrayList);
        client.shutdown();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}