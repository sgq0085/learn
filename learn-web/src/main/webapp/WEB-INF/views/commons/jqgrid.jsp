<%@ page pageEncoding="UTF-8"%>
<%--在官方的基础上做过修改--%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui-1.10.3.custom_for_jqgrid/jquery-ui-1.10.3.custom.css" />
<%--在官方的基础上做过修改--%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jqGrid-4.7.1/css/ui.jqgrid.custom.css"/>

<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui-1.10.3.custom_for_jqgrid/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jqGrid-4.7.1/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jqGrid-4.7.1/js/i18n/grid.locale-cn.js" ></script>
<%--自定义的jqGrid方法--%>
<script type="text/javascript" src="${ctx}/static/jquery/jqGrid-4.7.1/jqgrid-responsive.js"></script>

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

<%--显示消息用JS--%>
<script type="text/javascript">
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