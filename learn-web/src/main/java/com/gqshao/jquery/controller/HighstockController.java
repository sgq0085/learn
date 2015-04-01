package com.gqshao.jquery.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gqshao.jquery.entity.HighstockBean;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/highstock")
public class HighstockController {
    @RequestMapping("/test1")
    public String test1() {
        return "/highstock/test1";
    }

    @RequestMapping("/data")
    @ResponseBody
    public Map<String, Object> test4() {
        Map<String, Object> res = Maps.newHashMap();
        DateTime now = DateTime.now();
        List<HighstockBean> data1 = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            HighstockBean bean = new HighstockBean(UUID.randomUUID(), "b1." + i, now.plusHours(i).toDate(), 100 * Math.random());
            data1.add(bean);
        }
        res.put("data1", data1);


        List<HighstockBean> data2 = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            HighstockBean bean = new HighstockBean(UUID.randomUUID(), "b2." + i, now.plusHours(i).toDate(), 100 * Math.random());
            data2.add(bean);
        }
        res.put("data2", data2);
        return res;
    }
}