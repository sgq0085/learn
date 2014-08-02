//网站换肤
$(function(){
		var $li =$("#skin li");
		$li.click(function(){
			switchSkin( this.id );
		});
		var cookie_skin = $.cookie("MyCssSkin");
		if (cookie_skin) {
			switchSkin( cookie_skin );
		}
});

function switchSkin(skinName){
		$("#"+skinName).addClass("selected")                //当前<li>元素选中
					   .siblings().removeClass("selected");  //去掉其他同辈<li>元素的选中
	    $("#cssfile").attr("href","styles/skin/"+ skinName +".css"); //设置不同皮肤
		$.cookie( "MyCssSkin" ,  skinName , { path: '/', expires: 10 });
}