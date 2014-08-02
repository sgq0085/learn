$(document).ready(function() {
  $('div.member').on('mouseenter mouseleave', function(event) {
    var $image = $(this).find('img');
    if (!$image.is(':animated') || event.type == 'mouseleave') {
      var size = event.type == 'mouseenter' ? 85 : 75;
      var padding = event.type == 'mouseenter' ? 0 : 5;
      $image.animate({
        width: size,
        height: size,
        paddingTop: padding,
        paddingLeft: padding
      });
    }
  });
});
