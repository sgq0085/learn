//导航效果
$(function(){
   $("#nav li").hover(function(){
		$(this).find(".jnNav").show();
	},function(){
		$(this).find(".jnNav").hide();
	});
})