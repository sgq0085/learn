package com.gqshao.servlet;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 为表单Select元素提供随机option参数值
 */
public class SelectServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Map<String, Object> res = Maps.newHashMap();
        List<Map<String, String>> options = Lists.newArrayList();

        for (int i = 0; i < 15; i++) {
            Map<String, String> opt = Maps.newHashMap();
            opt.put("key", "key-2-" + i);
            opt.put("value", "value-2-" + i);
            options.add(opt);
        }
        res.put("options", options);
        String json = mapper.writeValueAsString(res);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            IOUtils.closeQuietly(out);
        }


    }
}