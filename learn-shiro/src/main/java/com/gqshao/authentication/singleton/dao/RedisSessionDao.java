package com.gqshao.authentication.singleton.dao;


import com.gqshao.redis.singleton.utils.JedisUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Collection;

/**
 * 对Shiro的Session的维护，因为使用Redis进行维护，所以不需要在缓存
 *
 * @see org.apache.shiro.session.mgt.SimpleSession
 */
public class RedisSessionDao extends AbstractSessionDAO {

    private static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    // 保存到Redis中key的前缀 prefix+sessionId
    private String prefix = "";
    // 设置会话的过期时间
    private int seconds = 0;

    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 根据会话ID获取会话
     *
     * @param sessionId 会话ID
     * @return ShiroSession
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        Session session = null;
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getResource();
            byte[] key = SerializationUtils.serialize(prefix + sessionId);
            byte[] value = jedis.get(key);
            if (value != null) {
                session = SerializationUtils.deserialize(value);
                logger.info("sessionId ttl : " + jedis.ttl(key));
                jedis.expire(key, seconds);
                logger.info("sessionId {} name {} 被读取", sessionId, session.getClass().getName());
            }
        } catch (Exception e) {
            logger.warn("读取Session失败", e);
        } finally {
            jedisUtil.close(jedis);
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
            jedis = jedisUtil.getResource();
            if (session instanceof SimpleSession) {
                byte[] key = SerializationUtils.serialize(prefix + sessionId);
                byte[] value = SerializationUtils.serialize(((SimpleSession) session));
                jedis.setex(key, seconds, value);
            } else if (session instanceof Serializable) {
                byte[] key = SerializationUtils.serialize(prefix + sessionId);
                byte[] value = SerializationUtils.serialize((Serializable) session);
                jedis.setex(key, seconds, value);
            } else {
                throw new RuntimeException("Session不能被序列化");
            }
        } catch (Exception e) {
            logger.warn("创建Session失败", e);
        } finally {
            jedisUtil.close(jedis);
        }
        return sessionId;
    }



    /**
     * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getResource();
            if (session instanceof SimpleSession) {
                byte[] key = SerializationUtils.serialize(prefix + session.getId());
                byte[] value = SerializationUtils.serialize(((SimpleSession) session));
                jedis.setex(key, seconds, value);
                logger.debug("sessionId {} name {} 被更新", session.getId(), session.getClass().getName());
            } else if (session instanceof Serializable) {
                byte[] key = SerializationUtils.serialize(prefix + session.getId());
                byte[] value = SerializationUtils.serialize((Serializable) session);
                jedis.setex(key, seconds, value);
                logger.debug("sessionId {} name {} 被更新", session.getId(), session.getClass().getName());
            } else {
                throw new RuntimeException("Session不能被序列化");
            }
        } catch (Exception e) {
            logger.warn("修改Session失败", e);
        } finally {
            jedisUtil.close(jedis);
        }
    }

    /**
     * 删除会话；当会话过期/会话停止（如用户退出时）会调用
     */
    @Override
    public void delete(Session session) {
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getResource();
            jedis.del(SerializationUtils.serialize(prefix + session.getId()));
        } catch (Exception e) {
            logger.warn("删除Session失败", e);
        } finally {
            jedisUtil.close(jedis);
        }
    }

    /**
     * 获取当前所有活跃用户，如果用户量多此方法影响性能
     */
    @Override
    public Collection<Session> getActiveSessions() {
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getResource();
            jedis.scan("ShiroSession_*");

        } catch (Exception e) {
            logger.warn("修改Session失败", e);
        } finally {
            jedisUtil.close(jedis);
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
