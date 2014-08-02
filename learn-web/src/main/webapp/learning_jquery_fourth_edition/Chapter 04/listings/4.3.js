$(document).ready(function() {
  var $speech = $('div.speech');
  $('#switcher-large').click(function() {
    var num = parseFloat($speech.css('fontSize'));
    num *= 1.4;
    $speech.css('fontSize', num + 'px');
  });
});
