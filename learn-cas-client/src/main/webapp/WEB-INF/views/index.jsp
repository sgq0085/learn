<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%@page import="org.jasig.cas.client.util.AssertionHolder" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>

<body>
Hi，下面是你的登录信息：
<%
    AttributePrincipal principal = null;
    if (AssertionHolder.getAssertion() != null) {
        principal = (AttributePrincipal) AssertionHolder.getAssertion().getPrincipal();
    }

    if (principal != null) {
        Map<String, Object> attributes = principal.getAttributes();

        if (attributes != null) {
            for (String key : attributes.keySet()) {
%>
<%=attributes.get(key) %>
<%
        }
    }
%>
<p><%=principal.getName() %>
</p>
<%
    }
    if (session != null) {
%>
<p>
    <%=
    session.getId()
    %>
</p>
<%
    }
%>

<a href="https://sso.gqshao.com:8443/cas-server/logout?service=<%=basePath%>logout">退出登录</a>

</body>
</html>