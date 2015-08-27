<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
  <title>DataTables简单DEMO1</title>
  <%@include file="/WEB-INF/views/commons/bootstrap-table.jsp" %>
</head>
<body>
<table id="table-javascript"></table>
<script>
    $(function(){
        $('#table-javascript').bootstrapTable({
//            method:"post",

        });
    });
</script>
</body>
</html>