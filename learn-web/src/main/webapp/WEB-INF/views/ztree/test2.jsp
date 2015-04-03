<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<html>
<head>
    <title>zTree简单JSON数据</title>
    <%@include file="/WEB-INF/views/commons/ztree.jsp" %>
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            view: {
                /*不显示连接线*/
                // showLine: false,
                /*不显示父节点图标*/
                // showIcon: showIconForTree
                fontCss: getFont,
                nameIsHTML: true
            },
            data: {
                /*简单 JSON 数据*/
                simpleData: {
                    enable: true
                }
            }
        };

        var zNodes = [
            {
                id: 1, pId: 0, name: "父节点1 - 展开", open: true
                /*定义图标 父节点自定义展开/折叠时图标的 URL 路径*/
                , iconOpen: "${ctx}/static/jquery/zTree-3.5.17/css/zTreeStyle/img/diy/1_open.png"
                , iconClose: "${ctx}/static/jquery/zTree-3.5.17/css/zTreeStyle/img/diy/1_close.png"
            },
            /*定义图标 节点自定义图标的 URL 路径*/
            {
                id: 11,
                pId: 1,
                name: "父节点11 - 折叠",
                icon: "${ctx}/static/jquery/zTree-3.5.17/css/zTreeStyle/img/diy/2.png"
            },
            {id: 111, pId: 11, name: "叶子节点111", icon: "${ctx}/static/jquery/zTree-3.5.17/css/zTreeStyle/img/diy/3.png"},
            {id: 112, pId: 11, name: "叶子节点112", icon: "${ctx}/static/jquery/zTree-3.5.17/css/zTreeStyle/img/diy/4.png"},
            {id: 113, pId: 11, name: "叶子节点113", icon: "${ctx}/static/jquery/zTree-3.5.17/css/zTreeStyle/img/diy/5.png"},
            {id: 114, pId: 11, name: "叶子节点114", icon: "${ctx}/static/jquery/zTree-3.5.17/css/zTreeStyle/img/diy/6.png"},
            {id: 12, pId: 1, name: "父节点12 - 折叠"},
            {id: 121, pId: 12, name: "叶子节点121 - 粗体字", font: {'font-weight': 'bold'}},
            {id: 122, pId: 12, name: "叶子节点122 - 斜体字", font: {'font-style': 'italic'}},
            {id: 123, pId: 12, name: "叶子节点123  - 有背景的字", font: {'background-color': 'black', 'color': 'white'}},
            {id: 124, pId: 12, name: "叶子节点124 - 红色字", font: {'color': 'red'}},
            {id: 124, pId: 12, name: "叶子节点125 - 蓝色字", font: {'color': 'blue'}},
            {
                id: 124,
                pId: 12,
                name: "<span style='color:blue;margin-right:0px;'>view</span>.<span style='color:red;margin-right:0px;'>nameIsHTML</span>"
            },
            {id: 124, pId: 12, name: "叶子节点126"},
            {id: 13, pId: 1, name: "父节点13 - 没有子节点", isParent: true},
            /*定义图标 节点自定义图标的 className
             treeNode.isParent 对应查找 _ico_open 和 _ico_close 两种class*/
            {id: 2, pId: 0, name: "父节点2 - 折叠", iconSkin: "pIcon01", open: true},
            {id: 21, pId: 2, name: "父节点21 - 展开", open: true, iconSkin: "pIcon02"},
            /*定义图标 节点自定义图标的 className
             !treeNode.isParent 对应查找 _ico_docu*/
            {id: 211, pId: 21, name: "叶子节点211", iconSkin: "icon01"},
            {id: 212, pId: 21, name: "叶子节点212", iconSkin: "icon02"},
            {id: 213, pId: 21, name: "叶子节点213", iconSkin: "icon03"},
            {id: 214, pId: 21, name: "叶子节点214", iconSkin: "icon04"},
            {id: 22, pId: 2, name: "父节点22 - 折叠", open: true},
            {id: 221, pId: 22, name: "叶子节点221 - URL", url: "www.baidu.com", target: "_blank"},
            {id: 222, pId: 22, name: "叶子节点222 - JS", click: "alert('我是不会跳转的...');"},
            {id: 223, pId: 22, name: "叶子节点223"},
            {id: 224, pId: 22, name: "叶子节点224"},
            {id: 23, pId: 2, name: "父节点23 - 折叠"},
            {id: 231, pId: 23, name: "叶子节点231"},
            {id: 232, pId: 23, name: "叶子节点232"},
            {id: 233, pId: 23, name: "叶子节点233"},
            {id: 234, pId: 23, name: "叶子节点234"},
            {id: 3, pId: 0, name: "父节点3 - 没有子节点", isParent: true}
        ];

        // 如果是父节点返回false
        function showIconForTree(treeId, treeNode) {
            return !treeNode.isParent;
        }

        // 返回node的字体 防止出现异常
        function getFont(treeId, node) {
            return node.font ? node.font : {};
        }

        $(document).ready(function () {
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
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
