<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ page import="org.apache.commons.lang3.StringUtils,org.apache.commons.io.IOUtils" %>
<%@ page import="java.io.PrintWriter,java.io.IOException" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>403 - 用户权限不足</title>
</head>
<%
    // 处理来自Ajax请求
    String requestType = request.getHeader("X-Requested-With");
    if (StringUtils.isNotBlank(requestType)) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.print("{\"msg\":\"用户权限不足\",\"success\":false}");
        } catch (IOException e) {
            //记录日志
            Logger logger = LoggerFactory.getLogger("403.jsp");
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(printWriter);
        }
    }
%>
<body>
<h2>403 - 用户权限不足.</h2>
<p><a href="<c:url value="/${ctx}"/>">返回首页</a></p>
</body>
</html>
