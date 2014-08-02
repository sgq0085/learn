console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

function outerFn() {
  var outerVar = 0;
  function innerFn1() {
    outerVar++;
    console.log('(1) outerVar = ' + outerVar);
  }
  function innerFn2() {
    outerVar += 2;
    console.log('(2) outerVar = ' + outerVar);
  }
  return {'fn1': innerFn1, 'fn2': innerFn2};
}
var fnRef = outerFn();
fnRef.fn1();
fnRef.fn2();
fnRef.fn1();
var fnRef2 = outerFn();
fnRef2.fn1();
fnRef2.fn2();
fnRef2.fn1();
