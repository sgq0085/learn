<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>日期选择框</title>
    <%@ include file="/WEB-INF/views/commons/datetimepicker.jsp"%>
</head>
<body>
<form id="form" action="${ctx}/excel/upload" class="form-horizontal" method="post"
      enctype="multipart/form-data">
    <div class="form-group">
        <label class="col-lg-3 col-md-3 control-label" for="recordDay">
            <span style="color: red">*</span>时间点：
        </label>

        <div class="col-lg-5 col-md-5">
            <div id="record" class="input-group date form_date">
                <input id="recordDay" class="form-control input-sm" name="recordDay" type="text"/>
                <span class="input-group-addon input-sm btn">
                    <i class="glyphicon glyphicon-calendar"></i>
                </span>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 col-md-3 control-label">时间范围：</label>

        <div class="col-lg-2 col-md-2">
            <div id="minDay" class="input-group date form_date" style="width:200px;">
                <input id="min" class="form-control input-sm" name="min" type="text"/>
                <span class="input-group-addon input-sm btn">
                    <i class="glyphicon glyphicon-calendar"></i>
                </span>
            </div>
        </div>

        <div class="col-lg-1 col-md-1" style="width:10px;margin-top：:30px;margin-left:30px;">—</div>

        <div class="col-lg-2 col-md-2">
            <div id="maxDay" class="input-group date form_date" style="width:200px;margin-left:20px">
                <input id="max" class="form-control input-sm" name="max" type="text"/>
                <span class="input-group-addon input-sm btn">
                    <i class="glyphicon glyphicon-calendar"></i>
                </span>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(function () {
        $('.form_date').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

        $("#minDay").datetimepicker().on('changeDate', function (ev) {
            $('#maxDay').datetimepicker('setStartDate', ev.date);
        });

        $("#maxDay").datetimepicker().on('changeDate', function (ev) {
            $('#minDay').datetimepicker('setEndDate', ev.date);
        });

    });
</script>
</body>
</html>
