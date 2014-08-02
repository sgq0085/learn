<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../layouts/default.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>

<div class="container">
    <h3>基本案例 <code>.table</code></h3>
    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" begin="1" end="3">
            <tr>
                <td>${i}</td>
                <td>first_${i}</td>
                <td>last_${i}</td>
                <td>first_last_${i}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>条纹状表格</h3>
    利用<code>.table-striped</code>可以给<code>&lt;tbody&gt;</code>之内的每一样增加斑马条纹样式。IE8不支持
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" begin="1" end="3">
            <tr>
                <td>${i}</td>
                <td>first_${i}</td>
                <td>last_${i}</td>
                <td>first_last_${i}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>带边框的表格</h3>
    利用<code>.table-bordered</code>为表格和其中的每个单元格增加边框
    <table class="table table-bordered ">
        <thead>
        <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" begin="1" end="3">
            <tr>
                <td>${i}</td>
                <td>first_${i}</td>
                <td>last_${i}</td>
                <td>first_last_${i}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>鼠标悬停</h3>
    利用<code>.table-hover</code>可以让<code>&lt;tbody&gt;</code>中的每一行响应鼠标悬停状态
    <table class="table table-hover">
        <thead>
        <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" begin="1" end="3">
            <tr>
                <td>${i}</td>
                <td>first_${i}</td>
                <td>last_${i}</td>
                <td>first_last_${i}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>紧缩表格</h3>
    利用<code>.table-condensed</code>可以让表格更加紧凑，单元格中的内部（padding）均会减半
    <table class="table table-condensed">
        <thead>
        <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" begin="1" end="3">
            <tr>
                <td>${i}</td>
                <td>first_${i}</td>
                <td>last_${i}</td>
                <td>first_last_${i}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>状态class 响应式表格</h3>
    通过这些状态class可以为行货单元格设置颜色。
    将任何<code>.table</code>包裹在<code>.table-responsive</code>中即可创建响应式表格，其会在小屏幕设备上（小于768px）水平滚动。
    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th>Class</th>
                <th>描述</th>
            </tr>
            </thead>
            <tbody>
            <tr class="active">
                <td>1</td>
                <td><code>.active</code></td>
                <td>鼠标悬停在行或单元格上时所设置的颜色</td>
            </tr>
            <tr class="success">
                <td>2</td>
                <td><code>.success</code></td>
                <td>标识成功或积极的动作</td>
            </tr>
            <tr class="warning">
                <td>3</td>
                <td><code>.warning</code></td>
                <td>标识警告或需要用户注意</td>
            </tr>
            <tr class="danger">
                <td>4</td>
                <td><code>.danger</code></td>
                <td>标识危险或潜在的带来负面影响的动作</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
