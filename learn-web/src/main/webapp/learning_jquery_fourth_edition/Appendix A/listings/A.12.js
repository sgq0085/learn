console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  $('#button-1, #button-2').show();
});

$(document).ready(function() {
  var counter = 0;
  $('#button-1').click(function(event) {
    event.preventDefault();
    counter++;
    console.log('counter = ' + counter);
  });
  $('#button-2').click(function(event) {
    event.preventDefault();
    counter--;
    console.log('counter = ' + counter);
  });
});
