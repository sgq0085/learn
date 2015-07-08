<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ page import="org.apache.commons.lang3.StringUtils,org.apache.commons.io.IOUtils" %>
<%@ page import="java.io.PrintWriter,java.io.IOException" %>
<%
    //设置返回码200，避免浏览器自带的错误页面
    response.setStatus(200);
    //记录日志
    Logger logger = LoggerFactory.getLogger("500.jsp");
    logger.error(exception.getMessage(), exception);

    // 处理来自Ajax请求
    String requestType = request.getHeader("X-Requested-With");
    if (StringUtils.isNotBlank(requestType)) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.print("{\"msg\":\"系统内部错误\",\"success\":false}");
        } catch (IOException e) {
            //记录日志
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(printWriter);
        }
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>500 - 系统内部错误</title>
</head>

<body>
<h2>500 - 系统发生内部错误.</h2>
</body>
</html>
