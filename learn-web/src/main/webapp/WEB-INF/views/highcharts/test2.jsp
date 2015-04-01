<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>jqgrid1</title>
    <%@include file="/WEB-INF/views/commons/highcharts.jsp" %>


</head>
<body>
<div id="container" style="min-width:800px;height:400px"></div>

<script type="text/javascript">
    $(function () {
        $.getJSON('${ctx}/highcharts/data', function (result) {
            $('#container').highcharts({
                chart: {
                    type: 'column'
                },
                credits: {
                    enabled: false
                },
                title: {text: 'Form JSON'},
                series: [{name: 'data1', data: result.data1}, {name: 'data2', data: result.data2}]
            });
        });
    });
</script>
</body>
</html>
