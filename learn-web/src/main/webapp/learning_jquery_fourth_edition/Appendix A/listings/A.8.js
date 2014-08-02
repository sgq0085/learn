console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

function outerFn() {
  var outerVar = 0;
  function innerFn() {
    outerVar++;
    console.log('outerVar = ' + outerVar);
  }
  return innerFn;
}
var fnRef = outerFn();
fnRef();
fnRef();
var fnRef2 = outerFn();
fnRef2();
fnRef2();
