package com.gqshao.authentication.dao;

import com.google.common.collect.Lists;
import com.gqshao.authentication.utils.SerializeUtils;
import com.gqshao.redis.component.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 先通过配置securityManager在属性cacheManager查找从缓存中查找Session是否存在，如果找不到才调用下面方法
 * Shiro内部相应的组件（DefaultSecurityManager）会自动检测相应的对象（如Realm）是否实现了CacheManagerAware并自动注入相应的CacheManager。
 */
public class CachingSessionDao extends CachingSessionDAO {

    private static final Logger logger = LoggerFactory.getLogger(CachingSessionDao.class);

    // 保存到Redis中key的前缀 prefix+sessionId
    private String prefix = "";

    // 设置会话的过期时间
    private int seconds = 0;

    @Autowired
    private JedisUtils jedisUtils;


    /**
     * 根据会话ID获取会话
     *
     * @param sessionId 会话ID
     * @return ShiroSession
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        Jedis jedis = null;
        try {
            jedis = jedisUtils.getResource();
            String key = prefix + sessionId;
            String value = jedis.get(key);
            if (StringUtils.isNotBlank(value)) {
                session = SerializeUtils.deserializeFromString(value);
                logger.info("sessionId ttl : " + jedis.ttl(key));
                jedis.expire(key, seconds);
                // 重新缓存到本地cache
                this.cache(session, sessionId);
            }
            logger.info("sessionId {} name {} 被读取", sessionId, session.getClass().getName());
        } catch (Exception e) {
            logger.warn("读取Session失败", e);
        } finally {
            jedisUtils.returnResource(jedis);
        }

        return session;
    }

    protected Session doReadSessionWithoutExpire(Serializable sessionId) {
        Session session = null;
        Jedis jedis = null;
        try {
            jedis = jedisUtils.getResource();
            String key = prefix + sessionId;
            String value = jedis.get(key);
            if (StringUtils.isNotBlank(value)) {
                session = SerializeUtils.deserializeFromString(value);
            }
        } catch (Exception e) {
            logger.warn("读取Session失败", e);
        } finally {
            jedisUtils.returnResource(jedis);
        }

        return session;
    }

    /**
     * 如DefaultSessionManager在创建完session后会调用该方法；
     * 如保存到关系数据库/文件系统/NoSQL数据库；即可以实现会话的持久化；
     * 返回会话ID；主要此处返回的ID.equals(session.getId())；
     */
    @Override
    protected Serializable doCreate(Session session) {
        // 创建一个Id并设置给Session
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        Jedis jedis = null;
        try {
            jedis = jedisUtils.getResource();
            jedis.setex(prefix + sessionId, seconds, SerializeUtils.serializeToString((SimpleSession) session));
            logger.info("sessionId {} name {} 被创建", sessionId, session.getClass().getName());
        } catch (Exception e) {
            logger.warn("创建Session失败", e);
        } finally {
            jedisUtils.returnResource(jedis);
        }
        return sessionId;
    }

    /**
     * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
     */
    @Override
    protected void doUpdate(Session session) {
        //如果会话过期/停止 没必要再更新了
        try {
            if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                return;
            }
        } catch (Exception e) {
            logger.error("ValidatingSession error");
        }

        Jedis jedis = null;
        try {
            if (session instanceof SimpleSession) {
                jedis = jedisUtils.getResource();
                jedis.setex(prefix + session.getId(), seconds, SerializeUtils.serializeToString((SimpleSession) session));
                logger.info("sessionId {} name {} 被更新", session.getId(), session.getClass().getName());
            } else if (session instanceof Serializable) {
                jedis = jedisUtils.getResource();
                jedis.setex(prefix + session.getId(), seconds, SerializeUtils.serializeToString((Serializable) session));
                logger.info("sessionId {} name {} 作为非SimpleSession对象被更新, ", session.getId(), session.getClass().getName());
            } else {
                logger.warn("sessionId {} name {} 不能被序列化 更新失败", session.getId(), session.getClass().getName());
            }
        } catch (Exception e) {
            logger.warn("更新Session失败", e);
        } finally {
            jedisUtils.returnResource(jedis);
        }
    }

    /**
     * 删除会话；当会话过期/会话停止（如用户退出时）会调用
     */
    @Override
    protected void doDelete(Session session) {
        Jedis jedis = null;
        try {
            jedis = jedisUtils.getResource();
            jedis.del(prefix + session.getId());
            logger.info("Session {} 被删除", session.getId());
        } catch (Exception e) {
            logger.warn("修改Session失败", e);
        } finally {
            jedisUtils.returnResource(jedis);
        }
    }

    /**
     * 获取当前所有活跃用户，如果用户量多此方法影响性能
     */
    @Override
    public Collection<Session> getActiveSessions() {
        Jedis jedis = null;
        try {
            jedis = jedisUtils.getResource();
            Set<String> keys = jedis.keys(prefix+"*");
            if (CollectionUtils.isEmpty(keys)) {
                return null;
            }
            List<String> valueList = jedis.mget(keys.toArray(new String[0]));
            SerializeUtils.deserializeFromStringController(valueList);
            List<Session> sessionList = Lists.newArrayList();
            return sessionList;
        } catch (Exception e) {
            logger.warn("统计Session信息失败", e);
        } finally {
            jedisUtils.returnResource(jedis);
        }
        return null;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

}
