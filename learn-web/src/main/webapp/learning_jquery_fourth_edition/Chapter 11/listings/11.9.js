$.fx.speeds._default = 250;

$(document).ready(function() {
  $('#fx-toggle').show().on('click', function() {
    $.fx.off = !$.fx.off;
  });

  var $movable = $('<div id="movable"></div>')
    .appendTo('body');

  var bioBaseStyles = {
        display: 'none',
        height: '5px',
        width: '25px'
      },
      bioEffects = {
        duration: 800,
        easing: 'easeOutQuart',
        specialEasing: {
          opacity: 'linear'
        }
      };

  function showDetails() {
    var $member = $(this);
    if ($member.hasClass('active')) {
      return;
    }

    $('div.member.active')
      .removeClass('active')
      .children('div').fadeOut();
    $member.addClass('active');

    $member.find('div').css({
      display: 'block',
      left: '-300px',
      top: 0
    }).each(function(index) {
      $(this).animate({
        left: 0,
        top: 25 * index
      }, {
        duration: 'slow',
        specialEasing: {
          top: 'easeInQuart'
        }
      });
    });
  }

  $('div.member').on('mouseenter mouseleave', function(event) {
    var size = event.type == 'mouseenter' ? 85 : 75;
    var padding = event.type == 'mouseenter' ? 0 : 5;
    $(this).find('img').stop().animate({
      width: size,
      height: size,
      paddingTop: padding,
      paddingLeft: padding
    });
  }).click(showDetails);
});
