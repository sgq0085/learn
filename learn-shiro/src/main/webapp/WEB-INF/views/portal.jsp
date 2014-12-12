<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>portal</title>
    <%--<style type="text/css">
        /* <![CDATA[ */
        /*css 内容*/
        /* ]]> */
    </style>

    <script type="text/javascript">
        // <![CDATA[
        // JavaScrip 内容
        // ]]>
    </script>--%>

</head>
<body>
<h2>Hello World!</h2>

<a href="${ctx}/logout">退出</a>

<script type="text/javascript">
    $(document).ready(
            function () {
                $(document).mousedown(function (e) {
                    console.log(e);
                    if (e.which == 3) {
                        e.preventDefault();
                        alert('禁用右键，弹出自己的');
                    }
                });
            }
    );
</script>

<%--
JS文件引入位置
<script type=”text/javascript” src=”JS文件”></script>
--%>

</body>
</html>
