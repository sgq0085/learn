/* 点击左侧产品小图片切换大图 */
$(function(){
	$("#jnProitem ul.imgList li a").bind("click",function(){
		  var imgSrc = $(this).find("img").attr("src");
		  var i = imgSrc.lastIndexOf(".");
		  var unit = imgSrc.substring(i);
		  imgSrc = imgSrc.substring(0,i);
		  var imgSrc_big = imgSrc + "_big"+ unit;
		  $("#thickImg").attr("href" , imgSrc_big);
	});
});