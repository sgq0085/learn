<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>WEB - <decorator:title default="Welcome!"/></title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

    <link type="image/x-icon" rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <script type="text/javascript" src="${ctx}/static/jquery/1.11.1/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <decorator:head/>
</head>
<body>
<decorator:body/>
</body>
</html>