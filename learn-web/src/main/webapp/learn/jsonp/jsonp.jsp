<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>JSONP</title>
    <script type="text/javascript">
        // <![CDATA[
        $(document).ready(function () {
            $.ajax({
                type: "get",
                async: false,
                url: "http://localhost:8080/learn-web/jsonp",
                dataType: "jsonp",
                jsonp: "callback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
                jsonpCallback: "jsoncallback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名
                success: function (json) {
                    $("#resText").text("callback = "+json.callback);
                },
                error: function () {
                    alert('fail');
                }
            });
        });
        // ]]>
    </script>
</head>
<body>
<span id="resText">
</span>
</body>
</html>
