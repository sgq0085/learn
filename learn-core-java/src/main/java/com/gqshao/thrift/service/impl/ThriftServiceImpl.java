package com.gqshao.thrift.service.impl;

import com.gqshao.thrift.domain.ThriftStruct;
import com.gqshao.thrift.service.ThriftService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThriftServiceImpl implements ThriftService.Iface {

    private static final Logger logger = LoggerFactory.getLogger(ThriftServiceImpl.class);

    @Override
    public String helloString(String param) throws TException {
        logger.info("helloString : " + param);
        return param;
    }

    @Override
    public int helloInt(int param) throws TException {
        logger.info("helloInt : " + param + ", begin sleep 1000");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("helloInt : " + param + ", begin sleep over");
        return param;
    }

    @Override
    public boolean helloBoolean(boolean param) throws TException {
        logger.info("helloBoolean : " + param);
        return param;
    }

    @Override
    public void helloVoid() throws TException {
        logger.info("helloVoid");
    }

    @Override
    public String helloNull() throws TException {
        logger.info("helloNull");
        return null;
    }

    @Override
    public int add(int n1, int n2) throws TException {
        logger.info("add n1 : " + n1 + ", n2" + n2);
        return n1 + n2;
    }

    @Override
    public int size(List<ThriftStruct> structs) throws TException {
        logger.info("size");
        if (CollectionUtils.isEmpty(structs)) {
            return 0;
        }
        return structs.size();
    }

    @Override
    public boolean sendSet(Set<ThriftStruct> structs) throws TException {
        logger.info("sendSet");
        if (CollectionUtils.isEmpty(structs)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean sendMap(Map<String, ThriftStruct> structs) throws TException {
        logger.info("sendMap");
        if (MapUtils.isEmpty(structs)) {
            return false;
        }
        return true;
    }
}
