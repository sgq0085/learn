<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page import="java.util.*" %>
<%@page import="org.jasig.cas.client.validation.Assertion" %>
<%@page import="org.jasig.cas.client.util.AbstractCasFilter" %>
<%@page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%@page import="org.jasig.cas.client.util.AssertionHolder" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
</head>
<body>
<shiro:user>
    已登录用户
    <shiro:hasRole name="admin">
        <div>该用户存在角色admin</div>
    </shiro:hasRole>
    <shiro:hasPermission name="show-info:show-div1">
        <div id="div1">div1</div>
    </shiro:hasPermission>
    <shiro:hasPermission name="show-info:show-div2">
        <div id="div2">div2</div>
    </shiro:hasPermission>
</shiro:user>
</body>
</html>