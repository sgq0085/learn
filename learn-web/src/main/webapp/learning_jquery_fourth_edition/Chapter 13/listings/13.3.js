$(document).ready(function() {
  var $ajaxForm = $('#ajax-form'),
      $response = $('#response');

  var buildItem = function(item) {
    var title = item.name,
        args = [],
        output = '<li>';

    if (item.type == 'method' || !item.type) {
      if (item.signatures[0].params) {
        $.each(item.signatures[0].params, function(index, val) {
          args.push(val.name);
        });
      }
      title = (/^jQuery|deferred/).test(title) ? title : '.' + title;
      title += '(' + args.join(', ') + ')';
    } else if (item.type == 'selector') {
      title += ' selector';
    }
    output += '<h3><a href="' + item.url + '">' + title + '</a></h3>';
    output += '<div>' + item.desc + '</div>';
    output += '</li>';

    return output;
  };

  $ajaxForm.on('submit', function(event) {
    event.preventDefault();

    $.ajax({
      url: 'http://book.learningjquery.com/api/',
      dataType: 'jsonp',
      data: {
        title: $('#title').val()
      },
      success: function(data) {
        console.log(data);
      }
    });
  });
});
