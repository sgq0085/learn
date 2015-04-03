package com.gqshao.jquery.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/highcharts")
public class HighchartsController {

    @RequestMapping("/test1")
    public String test1() {
        return "/highcharts/test1";
    }

    @RequestMapping("/test2")
    public String test2() {
        return "/highcharts/test2";
    }

    @RequestMapping("/data")
    @ResponseBody
    public Map<String, Object> test4() {
        Map<String, Object> res = Maps.newHashMap();
        List<Integer> data1 = Lists.newArrayList(3, 6, 8, 9, 10, 11);
        List<Integer> data2 = Lists.newArrayList(6, 7, 2, 6, 9, 9);
        res.put("data1", data1);
        res.put("data2", data2);
        return res;
    }

}