<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../layouts/default.jsp" %>
<html>
<head>
    <title>按钮</title>
</head>
<body>
<div class="container">
    <h2>按钮</h2>
    <!-- Standard button -->
    <button type="button" class="btn btn-default">默认</button>

    <!-- Provides extra visual weight and identifies the primary action in a set of buttons -->
    <button type="button" class="btn btn-primary">主要</button>

    <!-- Indicates a successful or positive action -->
    <button type="button" class="btn btn-success">成功</button>

    <!-- Contextual button for informational alert messages -->
    <button type="button" class="btn btn-info">信息</button>

    <!-- Indicates caution should be taken with this action -->
    <button type="button" class="btn btn-warning">警告</button>

    <!-- Indicates a dangerous or potentially negative action -->
    <button type="button" class="btn btn-danger">危险</button>

    <!-- Deemphasize a button by making it look like a link while maintaining button behavior -->
    <button type="button" class="btn btn-link">链接</button>

    <h3>尺寸</h3>

    <p>使用<code>.btn-lg</code>、<code>.btn-sm</code>、<code>.btn-xs</code>可以获得不同尺寸的按钮</p>

    <p>
        <button type="button" class="btn btn-primary btn-lg">Large button</button>
        <button type="button" class="btn btn-default btn-lg">Large button</button>
    </p>
    <p>
        <button type="button" class="btn btn-primary">Default button</button>
        <button type="button" class="btn btn-default">Default button</button>
    </p>
    <p>
        <button type="button" class="btn btn-primary btn-sm">Small button</button>
        <button type="button" class="btn btn-default btn-sm">Small button</button>
    </p>
    <p>
        <button type="button" class="btn btn-primary btn-xs">Extra small button</button>
        <button type="button" class="btn btn-default btn-xs">Extra small button</button>
    </p>

    <p>通过给按钮添加<code>.btn-block</code>可以使其充满父节点100%的宽度，而且按钮也变为了块级（block）元素。</p>

    <div class="row">
        <div class="col-md-offset-3 col-md-2">
            <button type="button" class="btn btn-primary btn-block">Block level button</button>
        </div>

        <div class="col-md-offset-3 col-md-2">
            <button type="button" class="btn btn-info btn-block">Block level button</button>
        </div>
    </div>

    <h3>活动状态 & 禁用状态</h3>

    <p>当按钮处于活动状态时，其表现为被按压下（底色更深，边框夜色更深，内置阴影）。通过<code>.active实现的</code></p>

    <p>为<code>button</code>添加<code>.disabled</code>属性</p>

    <p>
        <button type="button" class="btn btn-primary">正常</button>
        <button type="button" class="btn btn-primary active">活跃</button>
        <button type="button" class="btn btn-primary disabled">禁用</button>
    </p>

    <p>注意：下面是<code>a标签</code>，class只是改变<code>a</code>的外观，不影响功能</p>

    <p>
        <a href="#" class="btn btn-primary" role="button">正常</a>
        <a href="#" class="btn btn-primary active" role="button">活跃</a>
        <a href="#" class="btn btn-primary disabled" role="button">禁用</a>
    </p>

    <h3>可作按钮使用的HTML标签</h3>

    <p>可以为<code>a</code>、<code>button</code>或<code>input</code>元素添加按钮class</p>
    <a class="btn btn-default" href="#" role="button">Link</a>
    <button class="btn btn-default" type="submit">Button</button>
    <input class="btn btn-default" type="button" value="Input">
    <input class="btn btn-default" type="submit" value="Submit">
    <br><br><br><br><br><br><br><br><br>
</div>
</body>
</html>
