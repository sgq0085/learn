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
    console.log('Inner Function');
  }
}
console.log('innerFn():');
innerFn();
