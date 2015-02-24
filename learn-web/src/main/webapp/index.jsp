<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>

    <%--
    <style type="text/css">
        /* <![CDATA[ */
        /*css 内容*/
        /* ]]> */
    </style>

    <script type="text/javascript">
        // <![CDATA[
        // JavaScrip 内容
        // ]]>
    </script>
    --%>

</head>
<body>
<h2>Hello World!</h2>
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
