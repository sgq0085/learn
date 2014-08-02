console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  var readyVar = 0;
  function innerFn() {
    readyVar++;
    console.log('readyVar = ' + readyVar);
  }
  innerFn();
  innerFn();
});
