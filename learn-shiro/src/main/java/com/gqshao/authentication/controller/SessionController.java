package com.gqshao.authentication.controller;

import com.gqshao.authentication.dao.CachingShiroSessionDao;
import com.gqshao.authentication.session.ShiroSession;
import com.gqshao.redis.component.JedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private CachingShiroSessionDao sessionDao;

    @Autowired
    private JedisUtils jedisUtils;

    @RequestMapping("/active")
    @ResponseBody
    public Collection<Session> getActiveSessions() {
        return sessionDao.getActiveSessions();
    }

    @RequestMapping("/read")
    @ResponseBody
    public Session readSession(Serializable sessionId) {
        return sessionDao.doReadSessionWithoutExpire(sessionId);
    }

    @RequestMapping("/add")
    @SuppressWarnings("unchecked")
    public String add(String dataName, String dataValue) {
        // 把账号信息放到Session中，并更新缓存,用于会话管理
        Subject subject = SecurityUtils.getSubject();
        Serializable sessionId = subject.getSession().getId();
        ShiroSession session = (ShiroSession) sessionDao.doReadSessionWithoutExpire(sessionId);
        Map<String, String> customMap = (Map<String, String>) session.getAttribute("custom");
        customMap.put(dataName, dataValue);
        session.setAttribute(dataName, dataValue);
        sessionDao.update(session);

        // 通过发布消息通知其他节点取消本地对session的缓存
        jedisUtils.publish("shiro.session.uncache", session.getId());

        return "redirect:/";

    }
}
