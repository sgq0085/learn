<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../layouts/default.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>Glyphicons 图标</title>
</head>
<body>
<div class="container">
    <h3>Glyphicons 图标</h3>

    <p>为<code>span</code>元素添加<code>glyphicon glyphicon-xxxx</code>定义图标
        <br>
        把它们放在按钮，工具栏的按钮组中，导航或输入栏的前面都可以。
    </p>
    <span class="glyphicon glyphicon-cloud-upload"></span>

    <div role="toolbar" class="btn-toolbar">
        <div class="btn-group">
            <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-align-left"></span></button>
            <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-align-center"></span>
            </button>
            <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-align-right"></span></button>
            <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-align-justify"></span>
            </button>
        </div>
    </div>

    <div role="toolbar" class="btn-toolbar">
        <button class="btn btn-default btn-lg" type="button"><span class="glyphicon glyphicon-star"></span> Star
        </button>
        <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-star"></span> Star</button>
        <button class="btn btn-default btn-sm" type="button"><span class="glyphicon glyphicon-star"></span> Star
        </button>
        <button class="btn btn-default btn-xs" type="button"><span class="glyphicon glyphicon-star"></span> Star
        </button>
    </div>
</div>
</body>
</html>
