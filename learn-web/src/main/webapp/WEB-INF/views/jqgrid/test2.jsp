<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp" %>
<%--jqGrid研究二
主要完成如何设置发起ajax请求，设置按钮、单选框等功能--%>
<html>
<head>
    <title>jqgrid2</title>
    <%@include file="/WEB-INF/views/commons/jqgrid.jsp" %>
</head>
<body>
<div class="container">
    <div id="jqgrid" class="row">
        <%--jqGrid table list4--%>
        <table id="list"></table>
        <%--jqGrid 分页 div gridPager 如果一次展示全部数据 去掉该模块--%>
        <div id="pager"></div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var option = {
                    url: '${ctx}/jqgrid/test-data',
                    // 数据格式
                    datatype: "json",
                    // 提交方式
                    mtype: 'POST',
                    //URL中传参
                    postData: {
                        'customPostParam1': 'customPostValue1', 'custom2': 2
                    },
                    //高度，表格高度。可为数值、百分比或'auto'
                    height: 'auto',
                    //这个宽度不能为百分比
                    //width:1000,
                    //自动宽
                    autowidth: true,
                    // 第一列ID
                    colNames: ['', '单选', '姓名', '年龄', '性别', '日期'],
                    /*  colModel选项
                     name：为Grid中的每个列设置唯一的名称，这是一个必需选项，其中保留字包括subgrid、cb、rn。
                     index：设置排序时所使用的索引名称，这个index名称会作为sidx参数（prmNames中设置的）传递到Server。
                     label：当jqGrid的colNames选项数组为空时，为各列指定题头。如果colNames和此项都为空时，则name选项值会成为题头。
                     width：设置列的宽度，目前只能接受以px为单位的数值，默认为150。
                     sortable：设置该列是否可以排序，默认为true。
                     search：设置该列是否可以被列为搜索条件，默认为true。
                     resizable：设置列是否可以变更尺寸，默认为true。
                     hidden：设置此列初始化时是否为隐藏状态，默认为false。
                     formatter：预设类型或用来格式化该列的自定义函数名。常用预设格式有：integer、date、currency、number等*/
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
                    // 初始排序字段 对应提交参数 sidx
                    sortname: 'date',
                    // 排序顺序 默认 asc
                    sortorder: 'desc',
                    // 是否在浏览导航栏显示记录总数
                    viewrecords: true,
                    // 记录总数显示位置 要与navGrid中position不同
                    recordpos: 'left',
                    // 是否允许显示/隐藏按钮可用。只有标题不为空时可用
                    hidegrid: false,
                    // 设置为true时，表格只一次读取服务器数据（使用适当datatype），之后，datatype 自动变为local ，所有进一步操作都在客户端完成，pager功能（若存在）将失效。
                    loadonce: false,
                    // 添加左侧行号
                    rownumbers: true,
                    // 添加左侧多选
                    // multiselect: true,
                    // 对来自服务器的数据和提交数据进行encodes编码
                    autoencode: true,
                    // 设置为交替行表格,默认为false
                    altRows: true,
                    // 没有数据时右下角显示文本
                    emptyrecords: "没有可显示记录",
                    // Events 事件 请求完成后执行
                    loadComplete: function () {
                    }
                    ,
                    //表格某行被选中后执行的操作
                    onSelectRow: function (rowid, status) {
                        console.info("id", rowid);
                        // 去掉高亮显示效果
                        $('#' + rowid).removeClass('ui-state-highlight');
                        // 把表头单选框也选择上
                        var radio = $("#r_" + rowid);
                        radio.click();
                        console.info("radio", radio);
                    }
                    ,
                    // 事件 表格全部加载完成后触发
                    gridComplete: function () {
                        // 测试单选框正确性
                        $('input.tb_check').click(function (e) {
                            console.info(e);
                            var id = $('.tb_check:checked').attr('raid');
                            console.info(id);
                        });
                    }
                    ,
                    // 数组，用于设置jqGrid将要向Server传递的参数名称
                    prmNames: {
                        page: "page",    // 表示请求页码的参数名称
                        rows: "rows",    // 表示请求行数的参数名称
                        sort: "sidx", // 表示用于排序的列名的参数名称
                        order: "sord", // 表示采用的排序方式的参数名称
                        search: "_search", // 表示是否是搜索请求的参数名称
                        nd: "nd", // 表示已经发送请求的次数的参数名称
                        id: "id", // 表示当在编辑数据模块中发送数据时，使用的id的名称
                        oper: "oper",    // operation参数名称（我暂时还没用到）
                        editoper: "edit", // 当在edit模式中提交数据时，操作的名称
                        addoper: "add", // 当在add模式中提交数据时，操作的名称
                        deloper: "del", // 当在delete模式中提交数据时，操作的名称
                        subgridid: "id", // 当点击以载入数据到子表时，传递的数据名称
                        npage: null,
                        totalrows: "totalrows" // 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的
                    }
                    ,
                    jsonReader: {
                        root: "data", // json中代表实际模型数据的入口
                        page: "page", // json中代表当前页码的数据
                        total: "total", // json中代表页码总数的数据
                        records: "records", // json中代表数据行总数的数据
                        /*
                         如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
                         如果设为false，不但数据可以乱序，而且不用每个数据元素都要具备，用到哪个找到哪个就可以了*/
                        repeatitems: false,
                        /*cell、id在repeatitems为true时可以用到，即每一个记录是由一对id和cell组合而成，即可以适用另一种json结构
                         {"total":"xxx","curr":"yyy","records":"zzz",
                         "invdata" : [
                         // cell中不需要各列的name，只要值就OK了，但是需要保持对应
                         {"id" :"1", "cell" :["cell11", "cell12", "cell13"]},
                         {"id" :"2", "cell" :["cell21", "cell22", "cell23"]},... ]}
                         * */
                        cell: "cell",
                        id: "id",
                        userdata: "userdata",
                        //子级的内容 Action类中必须有与之匹配的属性
                        subgrid: {}
                    }


                }
                ;
        var list = $("#list");

        list.jqGrid(option);

        // 添加增删改按钮
        list.jqGrid('navGrid', '#pager', {edit: false, add: false, del: false, search: false, position: 'right'})
                .navButtonAdd('#pager', {
                    caption: "新增", buttonicon: "ui-icon-plus", onClickButton: function () {
                        alert("打开新增页面");
                    }, position: "last"
                })
                .navButtonAdd('#pager', {
                    caption: "修改", buttonicon: "ui-icon-pencil", onClickButton: function () {
                        // 返回id列表 jqGrid提供的多选
                        // var ids = $("#list").jqGrid('getGridParam', 'selarrrow');
                        // var ids = list.getGridParam('selarrrow');
                        var id = list.getGridParam("selrow");
                        // 单选
                        var ids = $('.tb_check:checked').attr('raid');
                        console.info("ids", ids);
                        if ($.isEmptyObject(ids) || ids.length > 1) {
                            bs_error('请选择一条数据,且仅选择一条数据');
                        } else {
                            alert("打开id=" + ids + "的修改页面");
                        }

                    }, position: "last"
                })
                .navButtonAdd('#pager', {
                    caption: "删除", buttonicon: "ui-icon-trash", onClickButton: function () {
                        alert("删除");
                    }, position: "last"
                });

        $('input.tb_check').click(function (e) {
            console.info(e);
            var id = $('.tb_check:checked').attr('raid');
            console.info(id);
        });

        //自适应
        jqgridResponsive("grid", false);


    });
</script>
</body>
</html>
