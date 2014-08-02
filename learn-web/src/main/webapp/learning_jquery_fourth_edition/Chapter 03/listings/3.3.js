$(document).ready(function() {
  $('#switcher-default')
  .addClass('selected')
  .on('click', function() {
    $('body').removeClass('narrow');
    $('body').removeClass('large');
    $('#switcher button').removeClass('selected');
    $(this).addClass('selected');
  });
  $('#switcher-narrow').on('click', function() {
    $('body').addClass('narrow');
    $('body').removeClass('large');
    $('#switcher button').removeClass('selected');
    $(this).addClass('selected');
  });
  $('#switcher-large').on('click', function() {
    $('body').removeClass('narrow');
    $('body').addClass('large');
    $('#switcher button').removeClass('selected');
    $(this).addClass('selected');
  });
});
