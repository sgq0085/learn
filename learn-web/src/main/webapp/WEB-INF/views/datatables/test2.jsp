<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>DataTables简单DEMO1</title>
    <%@include file="/WEB-INF/views/commons/datatables.jsp" %>

</head>
<body>
<div style="width: 500px;">
    <table id="table_id" class="display">
        <thead>
        <tr>
            <th>Column 1</th>
            <th>Column 2</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Row 1 Data 11111111111111111111111111111111111111</td>
            <td>Row 1 Data 22222222222222222222222222222222222222222222</td>
        </tr>
        <tr>
            <td>Row 2 Data 1</td>
            <td>Row 2 Data 2</td>
        </tr>
        <tr>
            <td>Row 3 Data 1</td>
            <td>Row 3 Data 2</td>
        </tr>
        <tr>
            <td>Row 4 Data 1</td>
            <td>Row 4 Data 2</td>
        </tr>
        <tr>
            <td>Row 5 Data 1</td>
            <td>Row 5 Data 2</td>
        </tr>
        <tr>
            <td>Row 6 Data 1</td>
            <td>Row 6 Data 2</td>
        </tr>
        <tr>
            <td>Row 7 Data 1</td>
            <td>Row 7 Data 2</td>
        </tr>
        <tr>
            <td>Row 8 Data 1</td>
            <td>Row 8 Data 2</td>
        </tr>
        <tr>
            <td>Row 9 Data 1</td>
            <td>Row 9 Data 2</td>
        </tr>
        <tr>
            <td>Row 10 Data 1</td>
            <td>Row 10 Data 2</td>
        </tr>
        <tr>
            <td>Row 11 Data 1</td>
            <td>Row 11 Data 2</td>
        </tr>
        <tr>
            <td>Row 12 Data 1</td>
            <td>Row 12 Data 2</td>
        </tr>
        <tr>
            <td>Row 13 Data 1</td>
            <td>Row 13 Data 2</td>
        </tr>


        </tbody>
    </table>
</div>

<script>
    $(function () {
        $('#table_id').DataTable({
            // 控制Datatables是否自适应宽度
            autowidth: true,
            // 控制是否显示表格左下角的信息
            info: false,
            // 是否允许用户改变表格每页显示的记录数
            lengthChangeDT:false,
            // 是否允许Datatables开启排序
            orderingDT:false,
            // 是否开启本地分页
            paging: false,
            // 是否显示处理状态
            processing: false,
            // 设置垂直滚动
            scrollY: 200,
            // 是否允许Datatables开启本地搜索
            Search:false
        });
    });
</script>
</body>
</html>
