package com.gqshao.jqgrid.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/jqgrid")
public class JqGridController {

    /**
     * test1
     */
    @RequestMapping("/test1")
    public String test1() {
        return "/jqgrid/test1";
    }

    /**
     * test2
     */
    @RequestMapping("/test2")
    public String test2() {
        return "/jqgrid/test2";
    }

    /**
     * test3
     */
    @RequestMapping("/test3")
    public String test3() {
        return "/jqgrid/test3";
    }

    @RequestMapping("/test-data")
    @ResponseBody
    public Map<String, Object> testData(String page) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("success", "true");
        // 当前页码
        if (StringUtils.isNotBlank(page)) {
            res.put("page", page);
        } else {
            res.put("page", 1);
        }
        // 总页数
        res.put("total", "5");
        // 数据行总数的数据
        res.put("records", 75);
        List<Map<String, Object>> datas = Lists.newArrayList();

        for (int i = 0; i < 15; i++) {
            Map<String, Object> data = Maps.newHashMap();
            data.put("id", UUID.randomUUID());
            data.put("name", data.get("id").toString().substring(0, 8));
            data.put("age", (int) (Math.random() * 80));
            data.put("sex", (int) (Math.random() * 2));
            data.put("date", new Date());
            datas.add(data);
        }
        res.put("data", datas);
        return res;
    }



}