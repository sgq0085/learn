$(document).ready(function() {
  var pageNum = 1;
  $('#more-photos').click(function(event) {
    event.preventDefault();
    var $link = $(this);
    var url = $link.attr('href');
    if (url) {
      $.get(url, function(data) {
        $('#gallery').append(data);
      });
      pageNum++;
      if (pageNum < 20) {
        $link.attr('href', 'pages/' + pageNum + '.html');
      }
      else {
        $link.remove();
      }
    }
  });

  $('#gallery').on('mouseover mouseout', function(event) {
    var $target = $(event.target).closest('div.photo');
    var $details = $target.find('.details');
    var $related = $(event.relatedTarget)
                   .closest('div.photo');

    if (event.type == 'mouseover' && $target.length) {
      $details.fadeTo('fast', 0.7);
    } else if (event.type == 'mouseout' && !$related.length) {
      $details.fadeOut('fast');
    }
  });
});

