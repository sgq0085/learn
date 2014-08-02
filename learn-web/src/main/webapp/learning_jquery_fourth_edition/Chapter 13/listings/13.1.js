$(document).ready(function() {
  var $ajaxForm = $('#ajax-form'),
      $response = $('#response');

  $ajaxForm.on('submit', function(event) {
    event.preventDefault();
    $response.load('http://api.jquery.com/ #content',
      $ajaxForm.serialize());
  });
});
