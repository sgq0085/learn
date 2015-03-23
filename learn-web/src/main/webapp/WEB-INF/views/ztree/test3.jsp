<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>zTree自动异步加载</title>
    <%@include file="/WEB-INF/views/commons/ztree.jsp" %>
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            async: {
                enable: true,
                url: "${ctx}/ztree/test3data",
                autoParam: ["id", "name", "level=lv"],
                otherParam: {"otherParam": "zTreeAsyncTest"},
                type: "post",
                dataType: "text",
                dataFilter: filter
            }
        };

        function filter(treeId, parentNode, childNodes) {
            if (!childNodes) return null;
            var length = childNodes.length;
            for (var i = 0, l = length; i < l; i++) {
                childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            }
            return childNodes;
        }

        $(document).ready(function () {
            $.fn.zTree.init($("#treeDemo"), setting);
        });
        //-->
    </SCRIPT>
</head>
<body>
<div class="row">
    <div class="col-lg-3 col-md-3">
        <ul id="treeDemo" class="ztree well" style="height: 498px; overflow: auto; -moz-user-select: none;"></ul>
    </div>

</div>
</body>
</html>
