package com.gqshao.servlet;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class JacksonTest {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Map<String, Object> res = Maps.newHashMap();
        res.put("title", "jsonp");
        List<String> attr = Lists.newArrayList();
        attr.add("attr1");
        attr.add("attr2");
        res.put("attr", attr);
        String json = mapper.writeValueAsString(res);

        Map messages = mapper.readValue(json, Map.class);

        System.out.println(1);
    }
}
