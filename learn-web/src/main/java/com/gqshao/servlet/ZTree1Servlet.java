package com.gqshao.servlet;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ZTree1Servlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("text/html;charset=utf-8");
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


        ObjectMapper mapper = new ObjectMapper();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String json = mapper.writeValueAsString(father);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        IOUtils.closeQuietly(out);
    }
}