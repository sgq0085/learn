/*使用jqzoom*/
$(function(){
	$('.jqzoom').jqzoom({
		zoomType: 'standard',
		lens:true,
		preloadImages: false,
		alwaysOn:false,
		zoomWidth: 340,
		zoomHeight: 340,
		xOffset:10,
		yOffset:0,
		position:'right'
    });
});