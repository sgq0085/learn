console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  $.each([0, 1, 2, 3, 4], function(index, value) {
    $('<div>Print ' + value + '</div>')
      .click(function() {
        console.log(value);
      }).insertBefore('#results');
  });
});
