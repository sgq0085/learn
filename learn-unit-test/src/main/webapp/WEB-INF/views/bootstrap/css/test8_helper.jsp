<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../layouts/default.jsp" %>
<html>
<head>
    <title>工具样式</title>
</head>
<body>
<div class="container"><br>

    <h3>上下文的颜色</h3>

    <p>通过少数实用类颜色传达强调意义。这些也可以应用到链接，就像默认的链接样式一样悬停变暗。</p>

    <p class="text-muted">text-muted 菜单文字</p>

    <p class="text-primary">text-primary 主要</p>

    <p class="text-success">text-success 成功</p>

    <p class="text-info">text-info 信息</p>

    <p class="text-warning">text-warning 警告</p>

    <p class="text-danger">text-danger 危险</p>

    <span class="text-info">个别情况下可以通过 <code>span</code> 包裹文字</span>
    <br>

    <h3>上下文背景</h3>

    <p class="bg-primary">bg-primary 主要</p>

    <p class="bg-success">bg-success 成功</p>

    <p class="bg-info">bg-info 信息</p>

    <p class="bg-warning">bg-warning 警告</p>

    <p class="bg-danger">bg-danger 危险</p>
    <br>

    <h3>关闭按钮</h3>

    <p>通过为<code>button</code>添加<code>.close</code></p>

    <div class="row">
        <div class="col-md-1">
            <button type="button" class="close" aria-hidden="true">&times;</button>
        </div>

    </div>
    <br>

    <h3>Carets</h3>

    <p>脱字符号 通过为<code>span</code>添加<code>.caret</code></p>
    <span class="caret"></span>

    <br>

    <h3>快速设置浮动、内容区域居中、清除浮动</h3>

    <p>通过class<code>.pull-left</code><code>.pull-right</code>设置快速设置浮动</p>

    <p>通过class<code>.center-block</code>设置内容区域居中</p>

    <p>通过class<code>.clearfix</code>清除浮动</p>


    <div class="pull-left">快速设置浮动pull-left;</div>
    <div class="pull-right">快速设置浮动pull-right;</div>
    <div class="center-block">内容区域居中center-block;</div>
    <div class="clearfix">清除浮动clearfix;</div>

    <br>

    <h3>显示和隐藏内容</h3>

    <p>通过class<code>.show</code><code>.hidden</code>设置显示和隐藏</p>

    <div class="show">show</div>
    <div class="hidden">hidden</div>

    <h1 class="text-hide">Custom heading</h1>

</div>


</body>
</html>
