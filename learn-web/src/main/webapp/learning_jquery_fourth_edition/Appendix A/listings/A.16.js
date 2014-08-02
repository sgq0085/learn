console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  for (var i = 0; i < 5; i++) {
    $('<div>Print ' + i + '</div>')
      .on('click', {value: i}, function(event) {
        console.log(event.data.value);
      }).insertBefore('#results');
  }
});
