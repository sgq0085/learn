package com.gqshao.thrift.client;

import com.google.common.collect.Lists;
import com.gqshao.thrift.domain.ThriftStruct;
import com.gqshao.thrift.server.ThriftServer;
import com.gqshao.thrift.service.ThriftService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ThriftClientService {

    private static final Logger logger = LoggerFactory.getLogger(ThriftClientService.class);

    public static void initTBinaryProtocolServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ThriftServer().initTBinaryProtocolServer();
            }
        }).start();
    }


    @Test
    public void testTBinaryProtocolClient() {
        TTransport transport = null;
        try {
            initTBinaryProtocolServer();
            transport = new TSocket("localhost", 7911);
            transport.open();
            // 设置传输协议为 TBinaryProtocol
            TProtocol protocol = new TBinaryProtocol(transport);
            ThriftService.Client client = new ThriftService.Client(protocol);
            // 调用服务的 helloVoid 方法
            client.helloString("world");
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    public static void initTCompactProtocolServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ThriftServer().initTCompactProtocolServer();
            }
        }).start();
    }

    @Test
    public void testTCompactProtocolClient() {
        TTransport transport = null;
        try {
            initTCompactProtocolServer();
            transport = new TSocket("localhost", 7912);
            transport.open();
            // 设置传输协议为 TCompactProtocol
            TProtocol protocol = new TCompactProtocol(transport);
            ThriftService.Client client = new ThriftService.Client(protocol);
            // 调用服务的 size 方法
            ThriftStruct s1 = new ThriftStruct();
            s1.setId(1);
            ThriftStruct s2 = new ThriftStruct();
            s2.setId(2);
            List<ThriftStruct> structs = Lists.newArrayList(s1, s2);
            int size = client.size(structs);
            logger.info("size === " + size);
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    public static void initTThreadPoolServerAndTCompactProtocolServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ThriftServer().initTThreadPoolServerAndTCompactProtocolServer();
            }
        }).start();
    }

    @Test
    public void testTThreadPoolServerAndTCompactProtocolServerClient() {
        initTThreadPoolServerAndTCompactProtocolServer();
        int threadNum = 50;
        final CountDownLatch threadSignal = new CountDownLatch(threadNum);
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 50; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    TTransport transport = null;
                    try {
                        // 传输层选用TSocket
                        transport = new TSocket("localhost", 7913);
                        transport.open();
                        // 设置传输协议为 TCompactProtocol
                        TProtocol protocol = new TCompactProtocol(transport);
                        ThriftService.Client client = new ThriftService.Client(protocol);
                        // 调用服务的 helloVoid 方法
//                        client.helloString("World !");
                        client.helloInt(finalI);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        transport.close();

                    }
                    threadSignal.countDown();
                }
            }).start();
        }
        try {
            threadSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("testTThreadPoolServerAndTCompactProtocolServerClient test over");
    }
}
