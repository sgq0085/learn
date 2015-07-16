<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>jqgrid1</title>

    <%@include file="/WEB-INF/views/commons/highstock.jsp" %>


</head>
<body>
<div id="container" style="min-width:800px;height:400px"></div>

<script type="text/javascript">$(function () {
    $.getJSON('${ctx}/highstock/data', function (result) {
        $('#container').highcharts('StockChart', {
            title: {text: 'My First Stock'},
            credits: {
                enabled: false
            },
            yAxis: {
                opposite: false,
                allowDecimals: false,
                labels: {
                    align: 'right', x: -3
                }
            },
            series: [{
                data: result.data1
            }, {
                data: result.data2
            }],
            rangeSelector: {
                buttons: [
                    {type: 'hour', count: 1, text: '1小时'},
                    {type: 'hour', count: 12, text: '12小时'},
                    {type: 'day', count: 1, text: '1天'},
                    {type: 'day', count: 3, text: '3天'},
                    {type: 'week', count: 1, text: '1周'},
                    {type: 'all', text: '所有'}
                ],
                selected: 0,
                inputEnabled: false
            },
            tooltip: {valueDecimals: 2}
        });
    });
});

</script>
</body>
</html>
