<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%--使用sitemesh统一页眉页脚菜单等，具体JSP中使用JSP指令元素include引入具体组件组装的JSP--%>
<%--<%@ include file="/WEB-INF/****/****.jsp"%>--%>

<%--添加标准模式（standard mode）的声明 CSS1Compat 而不是BackCompat --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--语言属性 根元素指定 lang 属性--%>
<html lang="zh-CN">
<head>
    <title>WEB - <sitemesh:write property='title'/></title>
    <%--字符编码 明确声明字符编码--%>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <%--IE兼容模式 通知IE采用其所支持的最新的模式--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%--国产浏览器默认采用高速模式webkit内核--%>
    <meta name="renderer" content="webkit">

    <link type="image/x-icon" rel="shortcut icon" href="${ctx}/static/images/favicon.ico">

    <%--HTML5中引入css文件的标签link的type属性默认值为"text/css"，所以不需要设置--%>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/3.2.0/css/bootstrap.min.css"/>

    <!--[if lt IE 9]>
    <script type="text/javascript" src="${ctx}/static/html5css3/html5shiv.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/html5css3/respond.min.js"></script>
    <![endif]-->

    <%--HTML5中引入JS文件的标签lscripttype属性默认值为"text/javascript"，所以不需要设置--%>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-1.11.1/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap/3.2.0/js/bootstrap.min.js"></script>

    <sitemesh:write property='head'/>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-default" id="top">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctx}">首页</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <%--<li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>--%>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">JqGrid<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${ctx}/jqgrid/test1">简单页面</a></li>
                        <li class="divider"></li>
                        <li><a href="${ctx}/jqgrid/test2">异步加载1</a></li>
                        <li><a href="${ctx}/jqgrid/test3">异步加载2</a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">zTree<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${ctx}/ztree/test1">简单页面1</a></li>
                        <li><a href="${ctx}/ztree/test2">简单页面2</a></li>
                        <li class="divider"></li>
                        <li><a href="${ctx}/ztree/test3">异步加载2</a></li>
                    </ul>
                </li>
                <li><a href="${ctx}/validation/init">Validation</a></li>
                <li><a href="${ctx}/autocomplete/init">AutoComplete</a></li>
                <li><a href="${ctx}/jsonp/init">JsonP</a></li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Bootstrap<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${ctx}/bootstrap/fileinput/init">fileinput</a></li>
                        <li class="divider"></li>
                        <li><a href="${ctx}/bootstrap/datetimepicker/init">datetimepicker</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-expanded="false">Form<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${ctx}/form/test1">简单表单1</a></li>
                        <li class="divider"></li>
                        <li><a href="${ctx}/form/test2">复杂表单1</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Highcharts<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${ctx}/highcharts/test1">普通Demo</a></li>
                        <li><a href="${ctx}/highcharts/test2">Json数据</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Highstock<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${ctx}/highstock/test1">普通Demo</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">DataTables<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${ctx}/datatables/test1">普通Demo1</a></li>
                        <li><a href="${ctx}/datatables/test2">普通Demo2</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>
    <sitemesh:write property='body'/>
</div>
</body>
</html>