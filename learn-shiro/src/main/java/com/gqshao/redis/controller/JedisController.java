package com.gqshao.redis.controller;

import com.google.common.collect.Lists;
import com.gqshao.redis.domain.SerializableBean;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping("/redis")
public class JedisController {

    private static final Logger logger = LoggerFactory.getLogger(JedisController.class);

    @Autowired
    private RedisTemplate template;


    @RequestMapping("/add")
    public boolean addLink(String userId, URL url) {
        List<SerializableBean> beans = Lists.newArrayList();
        SerializableBean b1 = new SerializableBean("id1", "n1", "v1", DateTime.now().toDate(), true);
        SerializableBean b2 = new SerializableBean("id2", "n2", "v2", DateTime.now().toDate(), false);
        beans.add(b1);
        beans.add(b2);
        String key = "beans";
        return true;
    }
}
