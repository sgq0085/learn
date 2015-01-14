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
 * 常用jqGrid返回值封装 包括 页码、总页数、总条目数、当前页数据
 */
public class JqGrid1Servlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String page = request.getParameter("page");
        ObjectMapper mapper = new ObjectMapper();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Map<String, Object> res = Maps.newHashMap();
        res.put("success", "true");
        // 当前页码
        if (StringUtils.isNotBlank(page)) {
            res.put("page", page);
        }else {
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
        String json = mapper.writeValueAsString(res);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        IOUtils.closeQuietly(out);

    }


}
