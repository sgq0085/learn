<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>jQuery Validation</title>
    <%@include file="../../commons/validation.jsp" %>


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
<button type="submit" class="btn btn-success" onclick="removeData()">去掉username验证信息</button>

<script type="text/javascript">
    // <![CDATA[

    $(function () {
        $("#username").focus();
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

    function activeValidate() {
        $("#fm1").validate().form();
    }

    function element() {
        $("#fm1").validate().element($("#username"));
    }

    function removeData() {
        $("#username").removeData("previousValue");
    }


    // ]]>
</script>
</body>
</html>
