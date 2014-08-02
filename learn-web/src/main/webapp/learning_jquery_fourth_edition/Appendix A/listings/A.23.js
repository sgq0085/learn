console.log = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  $('#button-1').show();
});

function hello() {
  console.log('hello');
  return false;
}
$(document).ready(function() {
  var button = document.getElementById('button-1');
  button.onclick = hello;
});
