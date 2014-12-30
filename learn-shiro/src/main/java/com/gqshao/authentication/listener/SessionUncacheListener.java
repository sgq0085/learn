package com.gqshao.authentication.listener;

import com.gqshao.authentication.dao.CachingShiroSessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

/**
 * Jedis实际的消息处理器
 */
public class SessionUncacheListener extends JedisPubSub {

    protected static Logger logger = LoggerFactory.getLogger(SessionUncacheListener.class);

    private CachingShiroSessionDao sessionDao;

    public SessionUncacheListener() {
    }

    public SessionUncacheListener(CachingShiroSessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    // 取得订阅的消息后的处理  
    public void onMessage(String channel, String message) {
        logger.info("从缓存失效通道{}得到sessionId:{} : ", channel, message);
        sessionDao.uncache(message);
    }

    // 初始化订阅时候的处理  
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("订阅缓存失效通道 : {} = {}", channel, subscribedChannels);
    }

    // 取消订阅时候的处理  
    public void onUnsubscribe(String channel, int subscribedChannels) {
        logger.info("取消对缓存失效通道消息的订阅 : {} = {}", channel, subscribedChannels);
    }

    public void setSessionDao(CachingShiroSessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }
}
