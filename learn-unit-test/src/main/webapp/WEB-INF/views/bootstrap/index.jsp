<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <link href="${ctx}/static/css/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctx}/static/javascript/commons/router.js"></script>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-2 col-md-offset-4"><h2>目录</h2></div>
    </div>
    <div class="row">
        <div class="col-md-3 col-md-offset-2">
            <h4>CSS</h4>
            <ul>
                <li><a href="javascript:router('bootstrap/css/test1_grid_system')">栅格系统</a></li>
                <li><a href="javascript:router('bootstrap/css/test2_typography')">排版</a></li>
                <li><a href="javascript:router('bootstrap/css/test3_code')">代码</a></li>
                <li><a href="javascript:router('bootstrap/css/test4_tables')">表格</a></li>
                <li><a href="javascript:router('bootstrap/css/test5_forms')">表单</a></li>
                <li><a href="javascript:router('bootstrap/css/test6_buttons')">按钮</a></li>
                <li><a href="javascript:router('bootstrap/css/test7_img')">图片</a></li>
                <li><a href="javascript:router('bootstrap/css/test8_helper')">工具样式</a></li>
            </ul>
        </div>

        <div class="col-md-3 col-md-offset-1">
            <h4>CSS</h4>
            <ul>
                <li><a href="javascript:router('bootstrap/components/test09_glyphicons')">Glyphicons 图标</a></li>

            </ul>
        </div>
    </div>
</div>

</body>
</html>