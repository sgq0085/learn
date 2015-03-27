<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>普通表单</title>
    <%@include file="/WEB-INF/views/commons/autocomplete.jsp" %>
</head>
<body>
<ul class="breadcrumb">
    <li class="active">表单Demo</li>
    <li class="active">普通表单1</li>
</ul>
<form id="searchForm" class="well form-inline" method="post">
    <span for="s_loginName">登录名：</span>
    &nbsp;&nbsp;
    <input type="text" id="s_loginName" name="s_loginName" class="form-control input-sm" placeholder="登录名"/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <span for="s_param1">可选项1：</span>
    &nbsp;&nbsp;
    <select id="s_param1" class="form-control input-sm" name="s_param1" <%--onchange="setOpt(this)"--%>>
        <option value="">-请选择-</option>
        <c:forEach items="${options1}" var="opt">
            <option value="${opt.value}">${opt.key}</option>
        </c:forEach>
    </select>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <span for="s_param2">可选项：</span>
    &nbsp;&nbsp;

    <select id="s_param2" class="form-control input-sm" name="s_param2">
        <option value="">-请选择-</option>
    </select>
    &nbsp;&nbsp;&nbsp;&nbsp;

    <span for="tags">自动填充：</span>
    &nbsp;&nbsp;
    <input type="text" id="tags" value="">
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button id="btnQuery" class="btn btn-primary btn-sm" type="button">查&nbsp;询</button>
</form>

<script type="text/javascript">
    $(function () {
        // 设置s_param2参数
        $.getJSON("${ctx}/select",
                function (data) {
                    var availableTags = [];
                    $.each(data.options, function (i, item) {
                        var opt = $("<option value=\"" + item.key + "\"></option>").html(item.value).appendTo("#s_param2");
                    });
                }
        );

        var availableTags = [
            "ActionScript",
            "AppleScript",
            "Asp",
            "BASIC",
            "C",
            "C++",
            "Clojure",
            "COBOL",
            "ColdFusion",
            "Erlang",
            "Fortran",
            "Groovy",
            "Haskell",
            "Java",
            "JavaScript",
            "Lisp",
            "Perl",
            "PHP",
            "Python",
            "Ruby",
            "Scala",
            "Scheme"
        ];
        $("#tags").autocomplete({
            source: availableTags
        });
    });
</script>
</body>
</html>
