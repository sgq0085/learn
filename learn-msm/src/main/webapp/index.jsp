<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="java.util.*" %>
<html>
<head><title>Cluster App Test</title></head>
<body>
Server Info:
<%
    out.println(request.getLocalAddr() + " : " + request.getLocalPort() + "<br>");%>
<%
    out.println("<br> ID " + session.getId() + "<br>");
    // 如果有新的 Session 属性设置
    String dataName = request.getParameter("dataName");
    if (dataName != null && dataName.length() > 0) {
        String dataValue = request.getParameter("dataValue");
        session.setAttribute(dataName, dataValue);
    }
    out.println("<b>Session 列表</b><br>");
    System.out.println("============================");
    Enumeration e = session.getAttributeNames();
    while (e.hasMoreElements()) {
        String name = (String) e.nextElement();
        String value = session.getAttribute(name).toString();
        out.println(name + " = " + value + "<br>");
        System.out.println(name + " = " + value);
    }
%>
<form action="index.jsp" method="POST">
    名称:<input type=text size=20 name="dataName">
    <br>
    值:<input type=text size=20 name="dataValue">
    <br>
    <input type=submit>
</form>
</body>
</html>