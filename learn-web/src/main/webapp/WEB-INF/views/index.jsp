<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>

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
<div class="jumbotron">
    <h1>Hello, world!</h1>
    <p>...</p>
    <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
</div>
<%--<h2>Hello World!</h2>--%>
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
