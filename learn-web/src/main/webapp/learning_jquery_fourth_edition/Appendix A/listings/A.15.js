console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  for (var i = 0; i < 5; i++) {
    (function(value) {
      $('<div>Print ' + value + '</div>')
        .click(function() {
          console.log(value);
        }).insertBefore('#results');
    })(i);
  }
});
