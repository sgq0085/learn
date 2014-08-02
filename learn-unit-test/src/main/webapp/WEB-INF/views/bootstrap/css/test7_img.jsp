<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../layouts/default.jsp" %>
<html>
<head>
    <title>图片</title>
</head>
<body>
<div class="container">
    <p>
        圆角<br>
        <img src="${ctx}/static/jpg/img.jpg" alt="图片" class="img-rounded">
    </p>

    <p>
        圆 —— 自动换行<br>
        <img src="${ctx}/static/jpg/img.jpg" alt="图片" class="img-circle"></p>

    <p>
        响应式图片<br>
        <img src="${ctx}/static/jpg/img.jpg" alt="图片" class="img-responsive">
    </p>

    <p>
        缩略图<br>
        <img src="${ctx}/static/jpg/img.jpg" alt="图片" class="img-thumbnail">
    </p>
</div>
</body>
</html>
