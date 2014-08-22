package com.gqshao.servlet;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class JsonpServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String callback = request.getParameter("callback");
        if (StringUtils.isBlank(callback)) {
            return;
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
        out.print(sb.append("(").append(json).append(")").toString());
        out.flush();
        out.close();
    }


}
