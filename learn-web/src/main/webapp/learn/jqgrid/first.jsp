<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>jqgrid</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/jquery-ui-1.10.3.custom.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/jqgrid/4.6.0/css/ui.jqgrid.css"/>

    <%--<style type="text/css">--%>
    <%--html, body {--%>
    <%--margin: 0;--%>
    <%--padding: 0;--%>
    <%--font-size: 75%;--%>
    <%--}--%>
    <%--</style>--%>

    <script type="text/javascript" src="${ctx}/static/jqgrid/4.6.0/js/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="${ctx}/static/jqgrid/4.6.0/js/jquery.jqGrid.min.js"></script>
    <%--<script type="text/javascript" src="${ctx}/static/jqgrid/jqgrid-responsive.js"></script>--%>

    <script type="text/javascript">
        $(document).ready(
                function () {
                    $("#list4").jqGrid({
                        datatype: "local",
                        height: 250,
                        colNames: ['Inv No', 'Date', 'Client', 'Amount', 'Tax', 'Total', 'Notes'],
                        colModel: [
                            {name: 'id', index: 'id', sorttype: "int"},
                            {name: 'invdate', index: 'invdate', sorttype: "date"},
                            {name: 'name', index: 'name'},
                            {name: 'amount', index: 'amount', align: "right", sorttype: "float"},
                            {name: 'tax', index: 'tax', align: "right", sorttype: "float"},
                            {name: 'total', index: 'total', align: "right", sorttype: "float"},
                            {name: 'note', index: 'note', sortable: false}
                        ],
                        pager: "#pager",
                        rowNum: 2,
                        rowList: [2, 3, 4],
                        multiselect: true,
                        caption: "Manipulating Array Data",
                        height: "100%",
                        autowidth: true

                    });
                    var mydata = [
                        {id: "1", invdate: "2007-10-01", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00"},
                        {id: "2", invdate: "2007-10-02", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00"},
                        {id: "3", invdate: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"},
                        {id: "4", invdate: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00"},
                        {id: "5", invdate: "2007-10-05", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00"},
                        {id: "6", invdate: "2007-09-06", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"},
                        {id: "7", invdate: "2007-10-04", name: "test", note: "note", amount: "200.00", tax: "10.00", total: "210.00"},
                        {id: "8", invdate: "2007-10-03", name: "test2", note: "note2", amount: "300.00", tax: "20.00", total: "320.00"},
                        {id: "9", invdate: "2007-09-01", name: "test3", note: "note3", amount: "400.00", tax: "30.00", total: "430.00"}
                    ];
                    for (var i = 0; i <= mydata.length; i++) {
                        $("#list4").jqGrid('addRowData', i + 1, mydata[i]);
                    }
                });
    </script>
</head>
<body>
<div class="container">
    <table id="list4"></table>
    <div id="pager"></div>
</div>
</body>
</html>
