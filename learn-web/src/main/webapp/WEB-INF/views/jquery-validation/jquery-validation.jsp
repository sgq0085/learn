<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>jQuery Validation</title>
    <%@include file="/WEB-INF/views/commons/validation.jsp" %>


</head>
<body>
<form id="fm1" action="jquery-validation.jsp" method="post">
    <div>
        <label for="username">用户名:</label>
        <input id="username" name="username" type="text" value="" class="required"/>
    </div>
    <div>
        <label for="password">密码:</label>
        <input id="password" name="password" type="password" value="" class="required"/>
    </div>
    <div>
        <input type="submit" name="submit" value="submit">
        <input type="reset" name="reset" value="reset">
    </div>
</form>
<button type="submit" class="btn btn-success" onclick="activeValidate()">检查</button>
<button type="submit" class="btn btn-success" onclick="element()">验证username</button>
<br>
<button type="submit" class="btn btn-success" onclick="reset()">重置表单验证信息</button>


<script type="text/javascript">
    // <![CDATA[

    $(function () {
        $("#username").focus();

        // 修改验证出错误之后显示标签label修改为其他标签
        $.extend($.validator.defaults, {
            errorElement: "span"
        });

        $("#fm1").validate({
            rules: {
                username: {
                    required: true,
                    minlength: 3,
                    maxlength: 30
                },
                password: {
                    required: true,
                    maxlength: 30,
                    minlength: 6
                }
            }
        });
    });

    //重置,包括错误提示
    $("input:reset").click(function () {
        $("#fm1").validate().resetForm();
    });

    // 手动验证
    function activeValidate() {
        $("#fm1").data("validator").form();
    }

    // 对单个元素清除缓存重新进行验证　一般用于远程调用
    function element() {
        var $username = $("#username");
        //　清除原来的认证消息
        $username.removeData("previousValue");
        // element方法中可以传入jQuery对象，或jQuery选择器字符串
        $("#fm1").data("validator").element($username);
        // $("#fm1").data("validator").element("#username");

    }

    // 重置表单验证信息
    function reset() {
        $("#fm1").validate().resetForm();
    }

    // ]]>
</script>
</body>
</html>
