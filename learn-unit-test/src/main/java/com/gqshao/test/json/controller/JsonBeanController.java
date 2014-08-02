package com.gqshao.test.json.controller;

import com.google.common.collect.Maps;
import com.gqshao.test.json.domain.JsonBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/json")
public class JsonBeanController {
    Logger logger = LoggerFactory.getLogger(JsonBeanController.class);

    @RequestMapping(value = "/info")
    @ResponseBody
    public Map<String, Object> info(@RequestBody JsonBean bean) {
        Map<String, Object> res = Maps.newHashMap();
        logger.info(bean.getBeanName());
        res.put("success", true);
        return res;
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> info(@PathVariable("id") String id, @RequestBody JsonBean bean) {
        Map<String, Object> res = Maps.newHashMap();
        logger.info(bean.getBeanName());
        res.put("success", true);
        return res;
    }

}
