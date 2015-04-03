package com.gqshao.bootstrap.controller;

import com.google.common.collect.Maps;
import com.gqshao.bootstrap.entity.TimeFilter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/bootstrap/datetimepicker")
public class DatetimepickerController {

    private static final DateTimeFormatter formater = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/init")
    public String init() {
        return "/bootstrap/datetimepicker";
    }

    @RequestMapping("/show")
    @ResponseBody
    public Map<String, Object> show(TimeFilter filter) {
        Map<String, Object> res = Maps.newHashMap();
        if (filter.getRecordDay() != null) {
            res.put("recordDay", new DateTime(filter.getRecordDay()).toString(formater));
        }
        if (filter.getMin() != null) {
            res.put("min", new DateTime(filter.getMin()).toString(formater));
        }
        if (filter.getMax() != null) {
            res.put("max", new DateTime(filter.getMax()).toString(formater));
        }
        return res;
    }
}