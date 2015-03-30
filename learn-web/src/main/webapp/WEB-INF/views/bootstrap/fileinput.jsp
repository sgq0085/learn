<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>文件导入</title>
    <%@ include file="/WEB-INF/views/commons/jquery-form.jsp" %>
    <%@ include file="/WEB-INF/views/commons/fileinput.jsp" %>
    <%@ include file="/WEB-INF/views/commons/bootstrap-message.jsp" %>
</head>
<body>
<form id="form" action="${ctx}/bootstrap/fileinput/upload" class="form-horizontal"
      method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label class="col-lg-3 col-md-3 control-label" for="other">其它信息：</label>

        <div class="col-lg-5 col-md-5">
            <input id="other" name="other" type="text" style="width: 100%;">
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 col-md-3 control-label" for="file_input">文件：</label>

        <div class="col-lg-5 col-md-5">
            <input id="file_input" name="file" type="file" multiple="multiple">
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 col-md-3 control-label" for="icon">图片：</label>

        <div class="col-lg-5 col-md-5">
            <input id="icon" name="icon" type="file">
        </div>
    </div>

    <div class="col-lg-offset-5  col-md-offset-5 col-lg-3 col-md-3">
        <button id="submit" type="button" class="btn btn-primary">提交</button>
        <button type="reset" class="btn btn-info">重置</button>
    </div>

</form>
<script type="text/javascript">
    $(function () {
        $("#file_input").fileinput({
            showCaption: true,
            showPreview: false,
            browseClass: "btn btn-primary",
            showUpload: false,
            fileType: "any"
        });

        $("#icon").fileinput({
            <%--<c:if test="${not empty app}">
            initialPreview: ["<img src='${ctx}//bootstrap/fileinput/icon/${app.id}/" + new Date().getTime() + "'  class='file-preview-image'>"],
            </c:if>--%>
            overwriteInitial: true,
            allowedPreviewTypes: ['image'],
            showUpload: false,
            showCaption: false,
            removeLabel: "删除",
            removeClass: "btn btn-danger",
            browseLabel: "选择图片",
            browseClass: "btn btn-primary",
            maxFileSize: 100,
            maxFilesNum: 10
        });


        $("#submit").click(function () {
            if ($("#file_input").val().length == 0) {
                bs_error("请选择要上传的文件");
                return;
            }
            bs_info('开始上传文件等待处理!');
            // 灰显提交按钮
            $("#submit").attr("disabled", true);
            //
            $("#form").ajaxSubmit({
                url: $("#form").attr("action") + "?timestamp=" + new Date().getTime(),
                success: function (event, status, xhr) {
                    /*// IE9特殊处理
                     if (event.indexOf("<pre>") > -1) {
                     event = event.substring(5, event.length - 6);

                     }
                     console.info(event);
                     // 为了兼容IE9 返回值类型为 text/plain;charset=UTF-8
                     event = JSON.parse(event);*/
                    if (event.success == true) {
                        bs_info(event.msg);
                        $("#submit").attr("disabled", false);
                    } else {
                        bs_error(event.msg);
                        $("#submit").attr("disabled", false);
                    }
                },
                error: function (event) {
                    bs_error("处理失败");
                    $("#submit").attr("disabled", false);
                }
            });
        });
    });
</script>
</body>
</html>
