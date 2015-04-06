package com.gqshao.authentication.singleton.service;

import com.gqshao.authentication.singleton.dao.CachingShiroSessionDao;
import com.gqshao.redis.service.PubSubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroSessionUncacheService implements PubSubService {

    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionUncacheService.class);

    @Autowired
    CachingShiroSessionDao sessionDao;

    @Override
    public void handle(String channel, String message) {
        logger.debug("channel {} , message {} ", channel, message);
        sessionDao.uncache(message);
    }
}