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
     */
    @RequestMapping("/test1")
    public String test0() {
        return "/ztree/test1";
    }

    @RequestMapping("test1data")
    @ResponseBody
    public Map<String, Object> test1Data() {
        Map<String, Object> father = Maps.newHashMap();
        List<Map<String, Object>> children = Lists.newArrayList();
        Map<String, Object> son1 = Maps.newHashMap();
        son1.put("name", "子节点1");
        Map<String, Object> son2 = Maps.newHashMap();
        children.add(son1);
        children.add(son2);

        son2.put("name", "子节点2");
        father.put("name", "父节点1");
        father.put("children", children);
        return father;
    }
}