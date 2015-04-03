package com.gqshao.ztree.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ztree")
public class ZTreeController {

    /**
     * test1
     * 最简单的DEMO
     */
    @RequestMapping("/test1")
    public String test1() {
        return "/ztree/test1";
    }

    /**
     * test2
     * 常用简单配置
     */
    @RequestMapping("/test2")
    public String test2() {
        return "/ztree/test2";
    }

    /**
     * test3
     * 异步加载数据1
     */
    @RequestMapping("/test3")
    public String test3() {
        return "/ztree/test3";
    }

    /**
     * 为异步加载提供数据
     */
    @RequestMapping("/test3data")
    @ResponseBody
    public List<Map<String, Object>> test3Data(String id,String name, Integer lv) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (id == null) {
            id = "0";
            name = "";
            lv = 0;
        } else {
            name = name + ".";
        }
        for (int i = 1; i < 5; i++) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("id", id + i);
            node.put("name", name + "n" + i);
            node.put("isParent", (i % 2 == 1 && lv < 2));
            list.add(node);
        }
        return list;
    }
}