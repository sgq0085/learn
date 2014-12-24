package com.gqshao.redis.controller;

import com.gqshao.redis.component.SubDaemonJob;
import com.gqshao.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redis")
public class RedisController {

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisService redisService;

    @RequestMapping("/test-string")
    public void testString() {
        redisService.test();
    }

}
