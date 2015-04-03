package com.gqshao.jquery.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jsonp")
public class JsonpController {

    /**
     * 页面初始化
     */
    @RequestMapping("/init")
    public String init() {
        return "/jsonp/jsonp";
    }

    /**
     * 组装数据
     */
    @RequestMapping("/jsoncallback1")
    @ResponseBody
    public String jsonCallBack1(String callback) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        if (StringUtils.isBlank(callback)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(callback);
        Map<String, Object> res = Maps.newHashMap();
        res.put("title", "jsonp");
        res.put("callback", callback);
        List<String> attr = Lists.newArrayList();
        attr.add("attr1");
        attr.add("attr2");
        res.put("attr", attr);
        String json = mapper.writeValueAsString(res);
        return sb.append("(").append(json).append(")").toString();
    }

    /**
     * 组装数据
     */
    @RequestMapping("/jsoncallback2")
    @ResponseBody
    public String jsonCallBack2(String callback) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        if (StringUtils.isBlank(callback)) {
            return "";
        }

        return new StringBuilder(callback).append("(").append(1).append(",").append(2).append(")").toString();
    }





}