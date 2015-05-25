package com.gqshao.thrift.server;

import com.gqshao.thrift.service.ThriftService;
import com.gqshao.thrift.service.impl.ThriftServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftServer {

    private static final Logger logger = LoggerFactory.getLogger(ThriftServer.class);


    /**
     * 二进制编码格式进行数据传输
     */
    public void initTBinaryProtocolServer() {
        try {
            // transport定义了消息是怎样在客户端和服务器端之间通信的
            TServerSocket serverTransport = new TServerSocket(7911);
            TSimpleServer.Args args = new TSimpleServer.Args(serverTransport);
            // 关联处理器与服务的实现
            TProcessor processor = new ThriftService.Processor<ThriftServiceImpl>(new ThriftServiceImpl());
            args.processor(processor);
            // 使用二进制来编码应用层的数据 protocol(协议)定义了消息是怎样序列化的
            args.protocolFactory(new TBinaryProtocol.Factory(true, true));
            // 使用普通的socket来传输数据
            args.transportFactory(new TTransportFactory());
            TServer server = new TSimpleServer(args);
            logger.info("Start TBinary Protocol Server on port 7911...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 高效率的、密集的二进制编码格式进行数据传输
     */
    public void initTCompactProtocolServer() {
        try {
            // transport定义了消息是怎样在客户端和服务器端之间通信的
            TServerSocket serverTransport = new TServerSocket(7912);
            TSimpleServer.Args args = new TSimpleServer.Args(serverTransport);
            // 关联处理器与服务的实现
            TProcessor processor = new ThriftService.Processor<ThriftServiceImpl>(new ThriftServiceImpl());
            args.processor(processor);
            // 使用二进制来编码应用层的数据 protocol(协议)定义了消息是怎样序列化的
            args.protocolFactory(new TCompactProtocol.Factory());
            // 使用普通的socket来传输数据
            args.transportFactory(new TTransportFactory());
            TServer server = new TSimpleServer(args);
            logger.info("Start TCompact Protocol Server on port 7911...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ThreadPoolServer 和 TCompactProtocol
     */
    public void initTThreadPoolServerAndTCompactProtocolServer() {
        try {
            TServerSocket serverTransport = new TServerSocket(7913);
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
            // 1.关联处理器与服务的实现
            TProcessor processor = new ThriftService.Processor<ThriftServiceImpl>(new ThriftServiceImpl());
            args.processor(processor);
            // 2.定义protocol 使用高效率的、密集的二进制编码格式进行数据传输 protocol(协议)定义了消息是怎样序列化的
            args.protocolFactory(new TCompactProtocol.Factory());
            // 使用普通的socket来传输数据 transport定义了消息是怎样在客户端和服务器端之间通信的
            // 默认 TTransportFactory
            // args.transportFactory(new TTransportFactory());
            // 实例化TThreadPoolServer
            TServer server = new TThreadPoolServer(args);
            logger.info("Start TCompact Protocol Server on port 7911...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
