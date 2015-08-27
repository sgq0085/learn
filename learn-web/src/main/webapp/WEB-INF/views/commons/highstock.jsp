<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/jquery/highstock-2.1.4/js/highstock.js"></script>
<script type="text/javascript">
    //必需动态设置的属性
    Highcharts.setOptions({
        credits: {enabled: false},
        rangeSelector: {enabled: false},
        global: {
            useUTC: true,
            timezoneOffset: -60 * (window.time_zone + 8) //设置时区偏差
        },
        colors: [
            '#7cb5ec', '#f7a35c', '#90ed7d', '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1', '#434348',  //Highcharts 4.x
            '#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4', //demo
            '#2f7ed8', '#0d233a', '#8bbc21', '#910000', '#1aadce', '#492970', '#f28f43', '#77a1e5', '#c42525', '#a6c96a',//Highcharts 3.x
            '#4572A7', '#AA4643', '#89A54E', '#80699B', '#3D96AE', '#DB843D', '#92A8CD', '#A47D7C', '#B5CA92'  //Highcharts 2.x
        ],
        chart: {
            // 图表边框宽度
            borderWidth: 1,
            // 绘制图形区域边框颜色
            borderColor: '#cccccc'
        },
        tooltip: {
            dateTimeLabelFormats: {
                millisecond: '%m-%d %H:%M:%S',
                second: '%m-%d %H:%M:%S',
                minute: '%m-%d %H:%M',
                hour: '%m-%d %H:%M',
                day: '%Y-%m-%d',
                week: '%Y-%m-%d',
                month: '%Y-%m',
                year: '%Y'
            },
            crosshairs: true,
            shared: true,
            xDateFormat: '%Y-%m-%d %H:%M'
        },
        xAxis: {
            dateTimeLabelFormats: {
                millisecond: '%m-%d,%H:%M:%S.%L',
                second: '%m-%d %H:%M:%S',
                minute: '%m-%d %H:%M',
                hour: '%m-%d %H:%M',
                day: '%m-%d ',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            },
            ordinal: true
        },
        legend: {
            enabled: true,
            y: -50,
            floating: true,
            borderWidth: 0,
            shadow: false
        },
        plotOptions: {
            series: {
                turboThreshold: 0
            }
        }

    });
</script>