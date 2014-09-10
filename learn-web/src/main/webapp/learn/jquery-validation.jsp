<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>jQuery Validation</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-validation-1.13.0/validate.css"/>
    <script type="text/javascript" src="${ctx}/static/jquery/1.11.1/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-validation-1.13.0/jquery.validate.js"></script>
    <script type="text/javascript"
            src="${ctx}/static/jquery/jquery-validation-1.13.0/localization/messages_zh.min.js"></script>

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
<button type="submit" class="btn btn-primary" onclick="ActiveValidate()">检查</button>

<script type="text/javascript">
    // <![CDATA[
    function ActiveValidate() {
        var myform = $("body").find("form").get(0);
        if (!$(myform).validate().form()) {
            return;
        }
    }
    $(function () {
        $("#username").focus();
        $.extend($.validator.defaults, {
            errorElement: "span"
        });
        $("#fm1").validate({rules: {
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
        }});


    });
    // ]]>
</script>
</body>
</html>
