<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.google.common.collect.Lists" %>
<%@ page import="com.google.common.collect.Maps" %>
<%--装载Options数据--%>
<%
    List<Map<String, String>> options = (List<Map<String, String>>) request.getAttribute("options");
    if (CollectionUtils.isEmpty(options)) {
        options = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Map<String, String> opt = Maps.newHashMap();
            opt.put("key", "key-1-" + i);
            opt.put("value", "value-1-" + i);
            options.add(opt);
        }
        request.setAttribute("options1", options);
    }
%>
<html>
<head>
    <title>jqgrid3</title>
    <%@include file="/WEB-INF/views/commons/jqgrid.jsp" %>
</head>
<body>
<form id="searchForm" class="well form-inline" method="post">
    登录名：
    &nbsp;&nbsp;&nbsp;
    <input type="text" id="s_loginName" name="s_loginName" class="form-control input-sm" placeholder="登录名"/>
    &nbsp;&nbsp;&nbsp;
    可选项1：
    &nbsp;&nbsp;&nbsp;
    <select id="s_param1" class="form-control input-sm" name="s_param1" <%--onchange="setOpt(this)"--%>>
        <option value="">请选择</option>
        <c:forEach items="${options1}" var="opt">
            <option value="${opt.value}">${opt.key}</option>
        </c:forEach>
    </select>
    &nbsp;&nbsp;&nbsp;
    可选项：
    &nbsp;&nbsp;&nbsp;

    <select id="s_param2" class="form-control input-sm" name="s_param2">
        <option value="">请选择</option>
    </select>
    &nbsp;&nbsp;&nbsp;
    <button id="btnQuery" class="btn btn-primary btn-sm" type="button">查&nbsp;询</button>
</form>

<div id="jqgrid">
    <table id="list"></table>
</div>

<script type="text/javascript">
    $(function () {
        // 设置select2参数
        $.getJSON("${ctx}/select",
                function (data) {
                    $.each(data.options, function (i, item) {
                        var opt = $("<option value=\"" + item.key + "\"></option>").html(item.value).appendTo("#s_param2");
                    });
                }
        );

        // 初始化jqGrid
        var option = {
            url: '${ctx}/jqgrid/test-data',
            datatype: "json",
            mtype: 'POST',
            postData: {
                'customPostParam1': 'customPostValue1', 'custom2': 2
            },
            height: 'auto',
            autowidth: true,
            colNames: ['', '单选', '姓名', '年龄', '性别', '日期'],
            colModel: [{name: 'id', index: 'id', hidden: true},
                {
                    name: '', index: '', width: 20, align: 'center',
                    formatter: function (cellvalue, option, rowObject) {
                        return '<input type="radio" id="r_' + rowObject.id + '" name="s_radio" class="tb_check" raid="' + rowObject.id + '">';
                    }
                },
                {name: 'name', index: 'name', align: 'left', sortable: false},
                {name: 'age', index: 'age', align: 'left'},
                {
                    name: 'sex',
                    index: 'sex',
                    align: 'center',
                    formatter: function (cellvalue, option, rowObjects) {
                        var result = '';
                        if (0 == cellvalue) {
                            result = '<span class="label label-success">男</span>'
                        } else if (1 == cellvalue) {
                            result = '<span class="label label-default">女</span>'
                        }
                        return result;
                    }
                },
                {
                    name: 'date', index: 'date', align: 'left',
                    formatter: function (cellvalue, option, rowObjects) {
                        return cellvalue != null ? new Date(cellvalue) : "";
                    }
                }],
            pager: '#pager',
            rowNum: 15,
            rowList: [15, 30, 50],
            sortname: 'date',
            sortorder: 'desc',
            viewrecords: true,
            recordpos: 'left',
            hidegrid: false,
            loadonce: false,
            autoencode: true,
            altRows: true,
            emptyrecords: "没有可显示记录",
            jsonReader: {
                root: "data",
                page: "page",
                total: "total",
                records: "records",
                repeatitems: false,
                id: "id"
            }
        };
        $("#list").jqGrid(option);

        // 监听查询方法并执行查询
        $("#btnQuery").click(function () {
            var grid = $("#list");
            var postData = grid.jqGrid("getGridParam", "postData");
            var mydata = {};
            var loginName = $("#s_loginName").val();
            var param1 = $("#s_param1").val();
            var param2 = $("#s_param2").val();
            console.info(loginName, param1, param2);
            if (loginName) {
                mydata.loginName = loginName;
            } else {
                delete postData.loginName;
            }
            if (param1) {
                mydata.param1 = param1;
            } else {
                delete postData.param1;
            }
            if (param2) {
                mydata.param2 = param2;
            } else {
                delete postData.param2;
            }
            $.extend(postData, mydata);
            grid.jqGrid("setGridParam", {
                search: true
            }).trigger("reloadGrid", [{page: 1}]);
        });
    });


</script>
</body>
</html>
