$.fx.speeds._default = 250;

$(document).ready(function() {
  $('#fx-toggle').show().on('click', function() {
    $.fx.off = !$.fx.off;
  });

  $('div.member').on('mouseenter mouseleave', function(event) {
    var size = event.type == 'mouseenter' ? 85 : 75;
    var padding = event.type == 'mouseenter' ? 0 : 5;
    $(this).find('img').stop().animate({
      width: size,
      height: size,
      paddingTop: padding,
      paddingLeft: padding
    });
  });
});
