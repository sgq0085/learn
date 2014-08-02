<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layouts/default.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>
<br>
<button type="button" class="btn btn-primary" onclick="commons()">普通请求</button>
<button type="button" class="btn btn-danger" onclick="likeRestfule()">Restfult风格</button>
<script>
    var data = {"id": "1111", "beanName": "test", "content": "the test bean"};
    function commons() {
        $.ajax({
            type: "POST",
            url: "json/info",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (data) {

            }
        });
    }

    function likeRestfule() {
        $.ajax({
            type: "POST",
            url: "json/info/" + data.id,
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (data) {

            }
        });
    }
</script>
</body>
</html>
