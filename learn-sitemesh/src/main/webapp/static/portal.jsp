<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>页面跳转测试</title>
</head>
<body>
<a href="<%=basePath%>" target="_blank">首页</a><br>
<a href="<%=basePath%>/static/portal.jsp" target="_blank">不被装饰的路径/static*</a><br>
<br>
<a href="javascript:router('index1',null,true)">全局装饰器装饰页一</a><br>
<a href="javascript:router('index2',null,true)">全局装饰器装饰页二</a><br>
<br>
<a href="<%=basePath%>/site1" target="_blank">通过匹配/site*，装饰页一</a><br>
<a href="<%=basePath%>/site2" target="_blank">通过匹配/site*，装饰页二</a><br>
<br>
<a href="<%=basePath%>/asite.html" target="_blank">通过匹配>*.html，装饰页一</a><br>
<a href="<%=basePath%>/asite.htm" target="_blank">通过匹配>*.htm，装饰页二</a><br>
<script src="<%=basePath%>/static/router.js"></script>
</body>
</html>