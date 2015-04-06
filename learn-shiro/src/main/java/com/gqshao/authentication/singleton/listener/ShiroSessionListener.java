package com.gqshao.authentication.singleton.listener;

import com.gqshao.authentication.singleton.dao.CachingShiroSessionDao;
import com.gqshao.redis.singleton.component.JedisUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroSessionListener implements SessionListener {

    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);

    @Autowired
    JedisUtils jedisUtils;

    @Autowired
    private CachingShiroSessionDao sessionDao;

    @Override
    public void onStart(Session session) {
        // 会话创建时触发
        logger.debug("session {} onStart", session.getId());
    }

    @Override
    public void onStop(Session session) {
        sessionDao.delete(session);
        jedisUtils.publish("shiro.session.uncache", session.getId());
        logger.debug("session {} onStop", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        sessionDao.delete(session);
        jedisUtils.publish("shiro.session.uncache", session.getId());
        logger.debug("session {} onExpiration", session.getId());
    }

}
