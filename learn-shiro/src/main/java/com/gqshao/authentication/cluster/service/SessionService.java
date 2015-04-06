package com.gqshao.authentication.cluster.service;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.gqshao.authentication.cluster.dao.CachingShiroSessionDao;
import com.gqshao.authentication.component.ShiroSession;
import com.gqshao.redis.cluster.utils.JedisUtil;
import com.gqshao.redis.service.PubSubService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

/**
 * 直接操作Session属性，不会被保存
 * 封装Session属性相关操作 Session属性发生改变时保存到Redis中并通知其它节点清空本地EhCache缓存
 */
public class SessionService implements PubSubService {

    private Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    private CachingShiroSessionDao sessionDao;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public void handle(String channel, String message) {
        logger.debug("channel {} , message {} ", channel, message);
        sessionDao.uncache(message);
    }

    public ShiroSession getSession() {
        return (ShiroSession) this.sessionDao.doReadSessionWithoutExpire(SecurityUtils.getSubject().getSession().getId());
    }


    public void setId(Serializable id) {
        ShiroSession session = this.getSession();
        session.setId(id);
        this.sessionDao.update(session);
        // 通过发布消息通知其他节点取消本地对session的缓存
        jedisUtil.publish("shiro.session.uncache", session.getId());
    }

    public void setStopTimestamp(Date stopTimestamp) {
        ShiroSession session = this.getSession();
        session.setStopTimestamp(stopTimestamp);
        this.sessionDao.update(session);
        // 通过发布消息通知其他节点取消本地对session的缓存
        jedisUtil.publish("shiro.session.uncache", session.getId());
    }

    public void setExpired(boolean expired) {
        ShiroSession session = this.getSession();
        session.setExpired(expired);
        this.sessionDao.update(session);
        // 通过发布消息通知其他节点取消本地对session的缓存
        jedisUtil.publish("shiro.session.uncache", session.getId());
    }

    public void setTimeout(long timeout) {
        ShiroSession session = this.getSession();
        session.setTimeout(timeout);
        this.sessionDao.update(session);
        // 通过发布消息通知其他节点取消本地对session的缓存
        jedisUtil.publish("shiro.session.uncache", session.getId());
    }

    public void setHost(String host) {
        ShiroSession session = this.getSession();
        session.setHost(host);
        this.sessionDao.update(session);
        // 通过发布消息通知其他节点取消本地对session的缓存
        jedisUtil.publish("shiro.session.uncache", session.getId());
    }

    public void setAttributes(Map<Object, Object> attributes) {
        ShiroSession session = this.getSession();
        session.setAttributes(attributes);
        this.sessionDao.update(session);
        // 通过发布消息通知其他节点取消本地对session的缓存
        jedisUtil.publish("shiro.session.uncache", session.getId());
    }

    public Map<Object, Object> getAttributes() {
        return this.getSession().getAttributes();
    }

    public void setAttribute(Object key, Object value) {
        ShiroSession session = this.getSession();
        session.setAttribute(key, value);
        this.sessionDao.update(session);
        // 通过发布消息通知其他节点取消本地对session的缓存
        jedisUtil.publish("shiro.session.uncache", session.getId());
    }

    public Object getAttribute(Object key) {
        return this.getSession().getAttribute(key);
    }

    public Collection<Object> getAttributeKeys() {
        return this.getSession().getAttributeKeys();
    }

    public Object removeAttribute(Object key) {
        ShiroSession session = this.getSession();
        Object res = session.removeAttribute(key);
        this.sessionDao.update(session);
        // 通过发布消息通知其他节点取消本地对session的缓存
        jedisUtil.publish("shiro.session.uncache", session.getId());
        return res;
    }

    /**
     * 在线会话的简单实现
     * 后续可以通过 keys统计数量，在通过SCAN 增量迭代取Session
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getActiveSessions() {
        List<Map<String, Object>> sessions = Lists.newLinkedList();
        Collection<Session> activeSession = sessionDao.getActiveSessions();
        for (Session session : activeSession) {
            Map<String, Object> map = Maps.newHashMap();
            SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

            if (principalCollection != null) {
                List<Object> listPrincipals = principalCollection.asList();
                Map<String, Object> attributes = (Map<String, Object>) listPrincipals.get(1);
                map.put("loginName", listPrincipals.get(0));
                map.put("name", attributes.get("name"));
                map.put("ip", attributes.get("ip"));
            } else {
                map.put("loginName", "未登录");
                map.put("name", "未登录");
                map.put("ip", "未登录");
            }
            map.put("startTimestamp", session.getStartTimestamp());
            map.put("lastAccessTime", session.getLastAccessTime());
            sessions.add(map);
        }
        return sessions;
    }


    public void flushRedis() {
        Collection<Session> activeSession = sessionDao.getActiveSessions();
        if (activeSession != null) {
            for (Session session : activeSession) {
                try {
                    sessionDao.doDelete(session);
                } catch (Exception e) {

                }
            }
        }
    }

    public void flushEhCache() {
        Set<Session> sessions = Sets.newHashSet();
        Collection<Session> ehCacheActiveSession = sessionDao.getEhCacheActiveSessions();
        Collection<Session> activeSession = sessionDao.getActiveSessions();
        if (CollectionUtils.isNotEmpty(ehCacheActiveSession)) {
            sessions.addAll(ehCacheActiveSession);
        }
        if (CollectionUtils.isNotEmpty(activeSession)) {
            sessions.addAll(activeSession);
        }
        for (Session session : sessions) {
            try {
                sessionDao.uncache(session.getId());
            } catch (Exception e) {
            }
        }
        logger.info("flushEhCache Project EhCacheActiveSessions {} ", sessionDao.getEhCacheActiveSessions().size());
    }

    public void flushAll() {
        Collection<Session> activeSession = sessionDao.getActiveSessions();
        if (activeSession != null) {
            for (Session session : activeSession) {
                try {
                    sessionDao.delete(session);
                } catch (Exception e) {
                }
            }
        }
    }
}
