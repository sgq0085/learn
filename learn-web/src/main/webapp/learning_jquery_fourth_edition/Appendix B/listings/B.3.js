/******************************************************************************
  Normally, tests would appear in a separate file named
  test/test.js, but for our examples it's convenient to place this
  test code in the same JavaScript file as the code that calls it.
******************************************************************************/

module('Selecting');

test('Child Selector', function() {
  expect(1);
  var topLis = $('#selected-plays > li.horizontal');
  equal(topLis.length, 3, 'Top LIs have horizontal class');
});

test('Attribute Selectors', function() {
  // tests go here
});

module('Ajax');

/******************************************************************************
  End test code; begin custom script code.
******************************************************************************/
$(document).ready(function() {
  $('#selected-plays > li').addClass('horizontal');
});
