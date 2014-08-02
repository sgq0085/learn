console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

var globalVar;

function outerFn() {
  console.log('Outer function');
  function innerFn() {
    console.log('Inner function');
  }
  globalVar = innerFn;
}
console.log('outerFn():');
outerFn();
console.log('globalVar():');
globalVar();
