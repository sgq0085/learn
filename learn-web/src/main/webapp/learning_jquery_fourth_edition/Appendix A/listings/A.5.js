console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

function outerFn() {
  console.log('Outer function');
  function innerFn() {
    console.log('Inner function');
  }
  return innerFn;
}
console.log('var fnRef = outerFn():');
var fnRef = outerFn();
console.log('fnRef():');
fnRef();
