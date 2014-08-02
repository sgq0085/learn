$(document).ready(function() {
  var $ajaxForm = $('#ajax-form'),
      $response = $('#response');

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
