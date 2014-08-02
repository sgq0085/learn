package com.gqshao.myapp.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.gqshao.myapp.demo.service.DemoService;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DemoService demoService;

    /**
     * 测试同时向两个数据库中保存
     * http://localhost/jta/demo/save
     */
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save() {
        Map<String, Object> res = Maps.newHashMap();
        String pu1Id = demoService.save();
        res.put("success", true);
        res.put("pu1", pu1Id);
        return res;
    }

    /**
     * 测试数据库回滚
     * http://localhost/jta/demo/exception
     */
    @RequestMapping("/exception")
    @ResponseBody
    public Map<String, Object> exception() {
        Map<String, Object> res = Maps.newHashMap();
        try {
            demoService.exception();
        } catch (Exception e) {

        }
        res.put("success", true);
        res.put("msg", "请查看数据库是否存入数据");
        return res;
    }
}
