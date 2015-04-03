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
        //图表展示容器，与div的id保持一致
        $('#container').highcharts({
            // 通用参数
            chart: {
                // 指定图表的类型，默认是折线图（line）
                type: 'column',
                // 指定此图是沿着什么轴进行放大
                zoomType: 'x',
                // 将X轴和Y轴进行调换
                inverted: true
            },
            // 图表版权信息
            credits: {
                // 不显示版权信息
                enabled: false
            },
            //指定图表标题
            title: {
                text: 'My first Highcharts chart',
                style: {
                    color: "#ff0000"
                }

            },
            //指定图表副标题
            subtitle: {
                text: '我是副标题'
            },
            // X轴
            xAxis: {
                title: {
                    text: 'x轴标题'
                },
                //指定x轴分组
//            categories: ['my', 'first', 'chart'],
                // 坐标轴标签
                labels: {
                    formatter: function () {
                        if (this.isFirst) {
//                        console.info(this.axis);
//                        console.info(this.chart);
                            return "first";
                        } else if (this.isLast) {
                            return "last";
                        } else {
                            return this.value;
                        }
                    }
                }
            },
            // Y轴
            yAxis: {
                title: {
                    text: 'something'                  //指定y轴的标题
                },
                // 坐标轴标签
                labels: {
                    formatter: function () {
                        if (this.value < 5) {
                            return "差[" + this.value + "]";
                        } else if (this.value < 7) {
                            return "中[" + this.value + "]";
                        } else {
                            return "优[" + this.value + "]";
                        }
                    }
                }
            },
            series: [{                                 //指定数据列
                name: 'Jane',                          //数据列名
                data: [1, 7, 9, 0, 4]                        //数据
            }, {
                name: 'John',
                data: [5, 1, 3, 7, 3]
            }]

        });
    });
</script>
</body>
</html>
