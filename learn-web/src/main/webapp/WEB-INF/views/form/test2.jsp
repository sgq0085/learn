<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>复杂表单</title>
    <%@include file="/WEB-INF/views/commons/autocomplete.jsp" %>
    <%@include file="/WEB-INF/views/commons/form.jsp" %>
    <style type="text/css">
        /* <![CDATA[ */
        .tree-select {
            background-color: #fff;
            display: block;
            position: absolute;
            padding: 5px;
            z-index: 970;
            border: 1px solid #ccc;
            border-radius: 5px;
            min-width: 190px;
            height: auto;
            min-height: 100px;
            max-height: 400px;
            overflow: auto;
        }

        /* ]]> */
    </style>
</head>
<body>
<ul class="breadcrumb">
    <li class="active">表单Demo</li>
    <li class="active">普通表单1</li>
</ul>
<form id="searchForm" method="post" class="dl_form" autocomplete="off">
    <dl class="col-16-3">
        <dt><span for="name">名称：</span></dt>
        <dd>
            <input type="text" id="name" name="name" maxlength="15" class="form-control input-sm"
                   onkeydown="return false;"/>
        </dd>
    </dl>
    <dl class="col-16-3">
        <dt><span for="param1">可选项：</span></dt>
        <dd>
            <select name="param1" id="param1" class="form-control input-sm">
                <c:forEach items="${options1}" var="opt">
                    <option value="${opt.value}">${opt.key}</option>
                </c:forEach>
            </select>
        </dd>
    </dl>
    <dl class="col-16-3">
        <dt><span for="tags">自动填充：</span></dt>
        <dd>
            <input type="text" id="tags" name="tags" value="" class="form-control input-sm">
        </dd>
    </dl>
    <dl class="col-16-3">
        <dt><span for="param2">自定义input下拉框：</span></dt>
        <dd>
            <input type="text" id="param2" name="param2" maxlength="15" class="form-control input-sm"
                   onkeydown="return false;"/>
        </dd>
    </dl>
    <%-- 留白 --%>
    <dl class="col-16-4 advanced" hidden="hidden">
        <dt><%--<span for="param3">多选框：</span>--%></dt>
        <dd>
            <%-- <input type="checkbox" id="param3" name="param3" value="1"/>--%>
        </dd>
    </dl>

    <dl class="col-16-3 advanced" hidden="hidden">
        <dt><span for="param4">选填：</span></dt>
        <dd>
            <input type="text" id="param4" name="param4" maxlength="15" class="form-control input-sm"
                   onkeydown="return false;"/>
        </dd>
    </dl>
    <dl class="col-16-3 advanced" hidden="hidden">
        <dt><span for="param5">选填：</span></dt>
        <dd>
            <input type="text" id="param5" name="param5" maxlength="15" class="form-control input-sm"
                   onkeydown="return false;"/>
        </dd>
    </dl>
    <dl class="col-16-3 advanced" hidden="hidden">
        <dt><%--<span for="param6">选填：</span>--%></dt>
        <dd>
            <%-- <input type="text" id="param6" name="param6" maxlength="15" class="form-control input-sm"
                    onkeydown="return false;"/>--%>
        </dd>
    </dl>
    <dl class="col-16-3 advanced" hidden="hidden">
        <dt><%--<span for="param7">选填：</span>--%></dt>
        <dd>
            <%--<input type="text" id="param7" name="param7" maxlength="15" class="form-control input-sm"
                   onkeydown="return false;"/>--%>
        </dd>
    </dl>


    <dl class="col-16-4">
        <dt></dt>
        <dd>
            <button type="button" id="btnQuery" class="btn btn-sm btn-primary">查询</button>
            <button type="button" id="reset_btn" class="btn btn-sm btn-primary">重置</button>
            <button type="button" id="toggle_btn" data-fold="1" class=" btn btn-xs btn-link">简单查询</button>
        </dd>
    </dl>
</form>

<script type="text/javascript">
    $(function () {

        /**
         * 为自动填充加载数据
         * 后端传入List，前端JS组装成数组 遍历拼接字符串的思路 只有最后一位后面不加,号
         */
        var autoComplete = [
            <c:forEach items="${autoComplete}" var="tree" varStatus="status">
            <c:choose>
            <c:when test="${status.last}">"${tree}"</c:when>
            <c:otherwise>"${tree}", </c:otherwise>
            </c:choose>
            </c:forEach>];

        $("#tags").autocomplete({
            source: autoComplete
        });

        /**
         * 高级查询/简单查询 切换
         */
        $("#toggle_btn").click(function () {
            var isfold = $(this).data('fold');
            if (isfold) {
                $(this).data('fold', 0);
                $('.advanced').show();
                $(this).text("高级查询");
            } else {
                $(this).data('fold', 1);
                $('.advanced').hide();
                $(this).text("简单查询");
            }
        });


        /**
         * 自定义input下拉框
         */
        var input = $("#param2");
        var _html = "<div style='min-width: " + input.outerWidth() + "px;' id='drop_down_div'>  </div>";
        var drop_down_div = $(_html);
        input.off("mousedown").on("mousedown", function (event) {
            if ($("#drop_down_div")[0]) {
                drop_down_div.show();
            } else {
                input.after(drop_down_div);
                $("#drop_down_div").addClass("tree-select");
                $("body").off('mousedown').on('mousedown', function (event) {
                    if (!(event.target.id == input.id ||
                            event.target.id == input.attr("id") ||
                            $(event.target).parents(input.attr("id")).length > 0)) {
                        drop_down_div.hide();
                    }
                });
            }
        });

    });
</script>
</body>
</html>
