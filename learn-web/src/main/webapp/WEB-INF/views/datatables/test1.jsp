<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>DataTables简单DEMO1</title>
    <%@include file="/WEB-INF/views/commons/datatables.jsp" %>

</head>
<body>
<table id="table_id" class="display">
    <thead>
    <tr>
        <th>Column 1</th>
        <th>Column 2</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>Row 1 Data 1</td>
        <td>Row 1 Data 2</td>
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
    </tbody>
</table>
<script>
    $(function () {
        $('#table_id').DataTable();
    });
</script>
</body>
</html>
