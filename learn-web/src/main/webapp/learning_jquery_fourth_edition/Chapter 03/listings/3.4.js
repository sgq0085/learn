$(document).ready(function() {
  $('#switcher-default')
  .addClass('selected')
  .on('click', function() {
    $('body').removeClass('narrow').removeClass('large');
  });
  $('#switcher-narrow').on('click', function() {
    $('body').addClass('narrow').removeClass('large');
  });
  $('#switcher-large').on('click', function() {
    $('body').removeClass('narrow').addClass('large');
  });

  $('#switcher button').on('click', function() {
    $('#switcher button').removeClass('selected');
    $(this).addClass('selected');
  });
});
