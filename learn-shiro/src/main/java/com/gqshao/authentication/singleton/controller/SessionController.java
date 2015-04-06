package com.gqshao.authentication.singleton.controller;


import com.gqshao.authentication.component.ShiroSession;
import com.gqshao.authentication.singleton.service.ShiroSessionService;
import org.apache.shiro.session.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

//@Controller
@RequestMapping("/session")
public class SessionController {

    private ShiroSessionService sessionService;

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

    public void setShiroSessionService(ShiroSessionService sessionService) {
        this.sessionService = sessionService;
    }
}

