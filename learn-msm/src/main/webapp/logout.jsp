<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" language="java"%>
<%@ page session="false" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    request.getSession().invalidate();
%>
</body>
</html>
