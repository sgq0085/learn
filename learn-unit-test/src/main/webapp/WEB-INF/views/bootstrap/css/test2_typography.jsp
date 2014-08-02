<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../layouts/default.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title>排版</title>
</head>
<body>
<div class="container">
    <h1>h1. 标题
        <small>副标题</small>
    </h1>
    <h6>h6. Bootstrap heading
        <small>Secondary text</small>
    </h6>

    普通文本
    <p>1/2行高的底部外边距 即 margin:0 0 10px</p>

    <p class="lead">通过添加<code>.lead</code>可以让段落突出显示</p>

    <p><code>small</code>标签包裹
        <small>其内的文本将被设置为父容器字体大小的85%</small>
    </p>

    <p>
        下面的这段文本
        <strong>进行了着重处理</strong>
    </p>

    <em>斜体</em>


    <p class="text-left">靠左</p>

    <p class="text-center">居中</p>

    <p class="text-right">靠右</p>


    <p class="text-muted">菜单文字</p>

    <p class="text-primary">主要</p>

    <p class="text-success">成功</p>

    <p class="text-info">信息</p>

    <p class="text-warning">警告</p>

    <p class="text-danger">危险</p>

    <p>缩略语-<abbr title="Bootstrap实现了对HTML的<abbr>元素的增强样式">基本缩略语</abbr></p>

    <p>缩略语-<abbr title="为缩略语添加.initialism可以将其font-size设置的更小些" class="initialism">.initialism</abbr></p>

    <blockquote class="pull-right">
        <p>默认样式使用了.pull-right引用</p>

        <p class="small">添加 <code>small</code>标签或<code>.small</code> 来注明引用来源</p>
    </blockquote>

    无序列表
    <ul>
        <li>苹果1</li>
        <li>苹果2</li>
        <li>苹果3</li>
    </ul>

    <abbr title="移除了默认的list-style样式和左侧外边距的一组元素（只针对直接子元素，只针对直接子元素">无样式列表</abbr>
    <ul class="list-unstyled">
        <li>苹果1</li>
        <li>苹果2</li>
        <li>苹果3</li>
    </ul>

    <abbr title="通过设置display: inline-block;并添加少量的内补，将所有元素放置于同一列">内联列表</abbr>
    <ul class="list-inline">
        <li>苹果1</li>
        <li>苹果2</li>
        <li>苹果3</li>
    </ul>

    有序列表
    <ol>
        <li>鸭梨1</li>
        <li>鸭梨2</li>
        <li>鸭梨3</li>
        <li>鸭梨4</li>
    </ol>

    描述-<abbr title="<dl><dt>...</dt><dd>...</dd></dl>">带有描述的短语列表</abbr>
    <dl>
        <dt>Description lists</dt>
        <dd>A description list is perfect for defining terms.</dd>
        <dt>Euismod</dt>
        <dd>Vestibulum id ligula porta felis euismod semper eget lacinia odio sem nec elit.</dd>
        <dd>Donec id elit non mi porta gravida at eget metus.</dd>
        <dt>Malesuada porta</dt>
        <dd>Etiam porta sem malesuada magna mollis euismod.</dd>
    </dl>

    <abbr title=".dl-horizontal可以让<dl>内短语及其描述排在一行。">水平排列的描述</abbr>
    <dl class="dl-horizontal">
        <dt>Description lists</dt>
        <dd>A description list is perfect for defining terms.</dd>
        <dt>Euismod</dt>
        <dd>Vestibulum id ligula porta felis euismod semper eget lacinia odio sem nec elit.</dd>
        <dd>Donec id elit non mi porta gravida at eget metus.</dd>
        <dt>Malesuada porta</dt>
        <dd>Etiam porta sem malesuada magna mollis euismod.</dd>
    </dl>
</div>
</body>
</html>
