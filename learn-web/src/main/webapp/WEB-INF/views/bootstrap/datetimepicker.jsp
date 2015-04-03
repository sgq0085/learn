<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>日期选择框</title>
    <%@ include file="/WEB-INF/views/commons/jquery-form.jsp" %>
    <%@ include file="/WEB-INF/views/commons/datetimepicker.jsp" %>
    <%@ include file="/WEB-INF/views/commons/bootstrap-message.jsp" %>
</head>
<body>

<form id="form" action="${ctx}/bootstrap/datetimepicker/show" class="form-horizontal" method="post">
    <div class="form-group">
        <label class="col-lg-3 col-md-3 control-label" for="recordDay">
            <span style="color: red">*</span>时间点：
        </label>

        <div class="col-lg-5 col-md-5">
            <div id="record" class="input-group date" readonly>
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
            <div id="maxDay" class="input-group date form_date" style="width:200px;margin-left:10px">
                <input id="max" class="form-control input-sm" name="max" type="text"/>
                <span class="input-group-addon input-sm btn">
                    <i class="glyphicon glyphicon-calendar"></i>
                </span>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 col-md-3 control-label">时间范围：</label>

        <div class="col-lg-2 col-md-2">
            <div id="start" class="input-group date form_date" style="width:200px;">
                <input id="start_time" class="form-control input-sm" name="start_time" type="text"/>
                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
        </div>

        <div class="col-lg-1 col-md-1" style="width:10px;margin-top：:30px;margin-left:30px;">—</div>

        <div class="col-lg-2 col-md-2">
            <div id="end" class="input-group date form_date" style="width:200px;margin-left:10px">
                <input id="end_time" class="form-control input-sm" name="max" type="text"/>
                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
        </div>
    </div>

    <div class="col-lg-offset-5  col-md-offset-5 col-lg-3 col-md-3">
        <button id="submit" type="submit" class="btn btn-primary">提交</button>
        <button type="reset" class="btn btn-info">重置</button>
    </div>
</form>
<script type="text/javascript">
    $(function () {
        $('#form').on('submit', function (e) {
            e.preventDefault(); // <-- important
        });

        $("#record").datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii',
            // 一周从哪一天开始
            weekStart: 1,
            // 当选择一个日期之后是否立即关闭此日期时间选择器
            autoclose: 1,
            startView: 2,
            forceParse: 0
        });

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

        function checkTime() {
            var start = $('#start_time').val();
            var end = $('#end_time').val()
            if (start && end) {
                return new Date(start) < new Date(end);
            } else {
                return true;
            }
        }

        $("#start").datetimepicker().on('changeDate', function (ev) {
            if (!checkTime()) {
                bs_error("开始时间不能大于结束时间");
            }
        });

        $("#end").datetimepicker().on('changeDate', function (ev) {
            if (!checkTime()) {
                bs_error("结束时间不能小于开始时间");
            }
        });


        $("#submit").click(function () {
            $("#submit").attr("disabled", true);
            /*$("#form").ajaxSubmit({
             url: $("#form").attr("action") + "?timestamp=" + new Date().getTime(),
             success: function (event, status, xhr) {
             bs_info("record : " + event.recordDay
             + ", min : " + event.min + ", max : " + event.max);
             $("#submit").attr("disabled", false);
             },
             error: function (event) {
             bs_error("处理失败");
             $("#submit").attr("disabled", false);
             }
             });*/
            $.ajax({
                type: "POST",
                url: $("#form").attr("action") + "?timestamp=" + new Date().getTime(),
                data: {
                    "recordDay": $("#recordDay").val(),
                    "max": $("#max").val(),
                    "min": $("#min").val()
                },
                success: function (event, status, xhr) {
                    $("#submit").attr("disabled", false);
                    bs_info("record : " + event.recordDay + ", min : " + event.min + ", max : " + event.max);

                },
                error: function (event) {
                    $("#submit").attr("disabled", false);
                    bs_error("处理失败");

                }
            });
        });
    });
</script>
</body>
</html>
