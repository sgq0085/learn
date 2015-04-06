package com.gqshao.authentication.cluster.controller;

import com.gqshao.authentication.cluster.service.SessionService;
import com.gqshao.authentication.component.ShiroSession;
import org.apache.shiro.session.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("/session")
public class SessionController {

    private SessionService sessionService;

    @RequestMapping("/active")
    @ResponseBody
    public List<Map<String, Object>> getActiveSessions() {
        return sessionService.getActiveSessions();
    }

    @RequestMapping("/read")
    @ResponseBody
    public Session readSession() {
        return sessionService.getSession();
    }

    @RequestMapping("/add")
    @SuppressWarnings("unchecked")
    public String add(String dataName, String dataValue) {
        // 把账号信息放到Session中，并更新缓存,用于会话管理
        ShiroSession session = sessionService.getSession();
        Map<String, String> customMap = (Map<String, String>) session.getAttribute("custom");
        customMap.put(dataName, dataValue);
        sessionService.setAttribute(dataName, dataValue);
        return "redirect:/";
    }

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }
}
