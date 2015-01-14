<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../commons/taglibs.jsp" %>
<%--jqGrid研究二
主要完成如何设置发起ajax请求，设置按钮、单选框等功能--%>
<html>
<head>
    <title>jqgrid2</title>
    <%@include file="../../commons/jqgrid.jsp" %>

    <%--显示消息用CSS--%>
    <style type="text/css">
        /* <![CDATA[ */
        #info-container-id {
            position: fixed;
            bottom: 5px;
            z-index: 9999;
            width: 70%;
            height: auto;
            margin-left: 15%;

        }

        #info-container-id .info-success {
            color: #468847;
            background-color: #dff0d8;
            border-color: #d6e9c6;
            border: 1px solid transparent;
            border-radius: 4px;
            margin-bottom: 2px;
            padding: 15px;
        }

        #info-container-id .info-danger {
            color: #b94a48;
            background-color: #f2dede;
            border-color: #eed3d7;
            border: 1px solid transparent;
            border-radius: 4px;
            margin-bottom: 2px;
            padding: 15px;
        }

        /* ]]> */
    </style>
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
            url: '${ctx}/jqgrid1',
            // 数据格式
            datatype: "json",
            // 提交方式
            mtype: 'POST',
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
            },
            //表格某行被选中后执行的操作
            onSelectRow: function (rowid, status) {
                console.info("id", rowid);
                // 去掉高亮显示效果
                $('#' + rowid).removeClass('ui-state-highlight');
                // 把表头单选框也选择上
                var radio = $("#r_" + rowid);
                radio.click();
                console.info("radio", radio);
            },
            // 事件 表格全部加载完成后触发
            gridComplete: function () {
                // 测试单选框正确性
                $('input.tb_check').click(function (e) {
                    console.info(e);
                    var id = $('.tb_check:checked').attr('raid');
                    console.info(id);
                });
            },
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
            },
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


        };
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


    /**
     showMessage({
			message:".....要显示的信息",
			isSuccess:false,
			callback:function(){
				//window.close();
			}
		});
     *
     *
     * @param message
     *            要提示的信息
     * @param isSuccess
     *            是正确信息（true）还是错误信息(false)
     */
    function showMessage(messageOrOptions, _isSuccess) {
        var defaultOpts = {
            //自动关闭
            autoClose: true,
            //非单例，每次执行都会新建
            singleton: false,
            //具体信息，可以是html
            message: null,
            //决定消息框的样式
            isSuccess: false,
            //是否允许消息重复
            isAllowRepeat: false,
            //窗体关闭回调
            callback: null,
            //信息的分类,该Class会加在infoDiv的class上
            //当为singleton，创建infoDiv会加入messageClass判断
            messageClass: ''
        };
        var containerId = 'info-container-id';
        //信息容器，所有的信息都放在这里
        var $container = $("#" + containerId);
        //此次生成的信息单元
        var $infoUnit;
        $container.length || ($container = $('<div id="' + containerId + '"    ></div>').appendTo('body'));


        if (typeof messageOrOptions === 'object') {
            $.extend(defaultOpts, messageOrOptions);
        } else {
            defaultOpts.message = messageOrOptions;
            defaultOpts.isSuccess = _isSuccess;
        }

        if (!defaultOpts.message) {
            defaultOpts.message = defaultOpts.isSuccess === true ? '成功，但没有提示信息！' : '失败，但没有提示信息！'
        }
        //判断消息是否重复
        var ru = null;
        if ((ru = repeatUnit(defaultOpts))) {
            if (!defaultOpts.singleton) {
                if (defaultOpts.autoClose) {
                    ru.remove();
                } else {
                    return;
                }
            }

        }
        //创建infoDiv,一条消息一个
        if (defaultOpts.singleton) {
            var $singletonInfo = $container.find(".info-singleton" + (defaultOpts.messageClass ? "." + defaultOpts.messageClass : ""));
            $singletonInfo.length || ($singletonInfo = $('<div class="info-unit info-singleton ' + defaultOpts.messageClass + '">' +
            '<button class="close close-info"  type="button">×</button>' +
            '<strong class="info-unit-message">提示信息</strong>' +
            '</div>').appendTo($container));
            $singletonInfo.on('click', '.close-info', {unit: $singletonInfo}, _close_info);
            enrichInfo($singletonInfo);
            $infoUnit = $singletonInfo;
        } else {
            var $notSingletonInfo = $('<div class="info-unit info-singleton-not  ' + defaultOpts.messageClass + '">' +
            '<button class="close close-info"   type="button">×</button>' +
            '<strong class="info-unit-message">提示信息</strong>' +
            '</div>').appendTo($container);
            $notSingletonInfo.on('click', '.close-info', {unit: $notSingletonInfo, isSingleton: false}, _close_info);
            enrichInfo($notSingletonInfo);
            $infoUnit = $notSingletonInfo;
        }
        return $infoUnit;


        //为消息DOM添加具体信息，样式，事件等
        function enrichInfo($info) {
            var $strong = $(".info-unit-message", $info);
            $strong.html(defaultOpts.message);

            if (defaultOpts.isSuccess === true) {
                $info.addClass('info-success').removeClass('info-danger');
            } else {
                $info.addClass('info-danger').removeClass('info-success');
            }
            $info.show();

            //显示的时间计算
            var mess_len = defaultOpts.message.length;
            var times = 400 * mess_len;
            //message可能是数字；最少显示1.5秒
            if (!$.isNumeric(times) || times < 1500) {
                times = 1500;
            }
            if (defaultOpts.autoClose === true)
                $info.slideDown(1000, function () {
                    /*// 计算展示高度
                     var strongHeight = $strong.height();
                     var ht = strongHeight > 50 ? strongHeight : 50;*/

                    // 拉起
                    window.setTimeout(function () {
                        $info.slideUp(1000, function () {
                            $info.find('.close:first').click();
                        });
                    }, times)

                });
        }


        function repeatUnit() {
            var __repeatUnit = null;
            var __msg = defaultOpts.message;
            var __msgHtml = $("<div>" + __msg + "</div>").html();
            var __$unit = null;

            $container.find('div.info-unit' + (defaultOpts.messageClass ? "." + defaultOpts.messageClass : "")).each(function () {
                __$unit = $(this);
                var $strong = __$unit.find(".info-unit-message");
                var __isRepeat = $strong.html() == __msg || $strong.html() == __msgHtml;
                if (__isRepeat) {
                    __repeatUnit = __$unit
                    return;
                }

            });
            return __repeatUnit;
        }

        function _close_info(event) {
            if (typeof messageOrOptions.callback === 'function') {
                messageOrOptions.callback();
            }

            if (event.data.isSingleton === false) {
                event.data.unit.remove();
            } else {
                event.data.unit.hide();
            }
        }


    }
    /**
     * 显示成功信息,自动关闭
     * @param message
     * @param callback
     */
    function bs_info(message, callback) {
        return showMessage({
            message: message,
            isSuccess: true,
            callback: callback
        });
    }
    /**
     * 显示错误信息,自动关闭
     * @param message
     * @param callback
     */
    function bs_error(message, callback) {
        return showMessage({
            message: message,
            isSuccess: false,
            callback: callback
        });
    }
</script>
</body>
</html>
