console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

function outerFn() {
  var outerVar = {};
  function innerFn() {
    console.log('hello');
  }
  outerVar.fn = innerFn;
  return innerFn;
};
