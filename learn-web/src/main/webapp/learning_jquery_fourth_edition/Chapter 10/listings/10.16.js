(function($) {
  $.event.special.throttledScroll = {
    setup: function(data) {
      var timer = 0;
      $(this).on('scroll.throttledScroll', function(event) {
        if (!timer) {
          timer = setTimeout(function() {
            $(this).triggerHandler('throttledScroll');
            timer = 0;
          }, 250);
        }
      });
    },
    teardown: function() {
      $(this).off('scroll.throttledScroll');
    }
  };

  $(document).on('mouseenter mouseleave', 'div.photo', function(event) {
    var $details = $(this).find('.details');
    if (event.type == 'mouseenter') {
      $details.fadeTo('fast', 0.7);
    } else {
      $details.fadeOut('fast');
    }
  });

  $(document).on('nextPage', function(event, scrollToVisible) {
    var url = $('#more-photos').attr('href');
    if (url) {
      $.get(url, function(data) {
        var $data = $(data).appendTo('#gallery');
        if (scrollToVisible) {
          var newTop = $data.offset().top;
          $(window).scrollTop(newTop);
        }
        checkScrollPosition();
      });
    }
  });

  var pageNum = 1;
  $(document).on('nextPage', function() {
    pageNum++;
    if (pageNum < 20) {
      $('#more-photos').attr('href', 'pages/' + pageNum + '.html');
    }
    else {
      $('#more-photos').remove();
    }
  });

  function checkScrollPosition() {
    var distance = $(window).scrollTop() + $(window).height();
    if ($('#container').height() <= distance) {
      $(document).trigger('nextPage');
    }
  }

  $(document).ready(function() {
    $('#more-photos').click(function(event) {
      event.preventDefault();
      $(this).trigger('nextPage', [true]);
    });

    var timer = 0;
    $(window).scroll(function() {
      if (!timer) {
        timer = setTimeout(function() {
          checkScrollPosition();
          timer = 0;
        }, 250);
      }
    }).trigger('scroll');
  });
})(jQuery);
