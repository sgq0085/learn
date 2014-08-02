<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../layouts/default.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>栅格系统</title>
    <link href="${ctx}/static/css/commons/custom.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%--xs:超小型 sm:小型 768 md:中型 992 lg:大型 1200--%>
<div class="container">
    <div class="row">
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
        <div class="col-xs-1 col-sm-1 col-md-1">.col-xs-1 .col-sm-1 .col-md-1</div>
    </div>

    列偏移
    <div class="row">
        <div class="col-md-3 col-md-offset-3">col-md-3 col-md-offset-3</div>
        <div class="col-md-2 col-md-offset-2">col-md-2 col-md-offset-2</div>
    </div>

    <div class="row">
        <div class="col-xs-12 col-sm-6 col-md-8">.col-xs-12 .col-sm-6 .col-md-8</div>
        <div class="col-xs-6 col-md-4">.col-xs-6 .col-md-4</div>
    </div>
    <div class="row">
        <div class="col-xs-6 col-sm-4">.col-xs-6 .col-sm-4</div>
        <div class="col-xs-6 col-sm-4">.col-xs-6 .col-sm-4</div>
        <!-- Optional: clear the XS cols if their content doesn't match in height -->
        <div class="clearfix visible-xs"></div>
        <div class="col-xs-6 col-sm-4">.col-xs-6 .col-sm-4</div>
    </div>


    <div class="row">
        <div class="col-xs-6 col-sm-3">.col-xs-6 .col-sm-3</div>
        <div class="col-xs-6 col-sm-3">.col-xs-6 .col-sm-3</div>

        <!-- Add the extra clearfix for only the required viewport -->
        <div class="clearfix visible-lg">clearfix visible-lg <br/>只有当lg时显示</div>

        <div class="col-xs-6 col-sm-3">.col-xs-6 .col-sm-3</div>
        <div class="col-xs-6 col-sm-3">.col-xs-6 .col-sm-3</div>
    </div>

    嵌套列
    <div class="row">
        <div class="col-md-6">col-md-6
            <div class="row">
                <div class="col-md-6">col-md-6</div>
                <div class="col-md-6">col-md-6</div>
            </div>
        </div>
    </div>

    列排序
    <div class="row">
        <div class="col-md-4 col-md-push-2">.col-md-9 .col-md-push-3 <br/>向后推 本col 其它不动 可能产生重叠</div>
        <div class="col-md-2">.col-md-2</div>
        <div class="col-md-2 col-md-pull-6">.col-md-2 .col-md-pull-6 <br/>向前拽 本col 其它不动 可能产生重叠</div>
    </div>


</div>
</body>
</html>
