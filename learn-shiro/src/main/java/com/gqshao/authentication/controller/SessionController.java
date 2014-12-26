package com.gqshao.authentication.controller;

import com.gqshao.authentication.dao.CachingShiroSessionDao;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.Collection;

@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private CachingShiroSessionDao sessionDao;

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
}
