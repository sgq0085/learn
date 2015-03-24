<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>JSONP</title>
    <script type="text/javascript">
        // <![CDATA[
        $(document).ready(function () {
            $.ajax({
                type: "get",
                async: false,
                url: "${ctx}/jsonp/jsoncallback1",
                dataType: "jsonp",
                jsonp: "callback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
                jsonpCallback: "jsoncallback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名
                success: function (json) {
                    $("#resText").text("callback = " + json.callback);
                },
                error: function () {
                    alert('fail');
                }
            });
        });

        function nativeMethod(data1, data2) {
            alert(data1 == data2);
        }
        // ]]>
    </script>
    <script type="text/javascript" src="${ctx}/jsonp/jsoncallback2?callback=nativeMethod"></script>
</head>
<body>
<span id="resText">
</span>
</body>
</html>
