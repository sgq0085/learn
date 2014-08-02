/******************************************************************************
  Normally, tests would appear in a separate file named
  test/test.js, but for our examples it's convenient to place this
  test code in the same JavaScript file as the code that calls it.
******************************************************************************/

module('Selecting', {
  setup: function() {
    this.topLis = $('#selected-plays > li.horizontal');
  }
});

test('Child Selector', function() {
  expect(1);
  equal(this.topLis.length, 3, 'Top LIs have horizontal class');
});

test('Attribute Selectors', function() {
  expect(2);
  ok(this.topLis.find('.mailto').length == 1, 'a.mailto');
  equal(this.topLis.find('.pdflink').length, 1, 'a.pdflink');
});

module('Ajax');

asyncTest('JSON', function() {
  expect(2);
  var backbite = {
    "term": "BACKBITE",
    "part": "v.t.",
    "definition": "To speak of a man as you find him when he can't find you."
  };

  $.getJSON('B.json', function(json, textStatus) {
    equal(textStatus, 'success', 'Request successful');
    deepEqual(json[1], backbite, 'result array matches "backbite" map');
  }).always(function() {
    start();
  });
});

/******************************************************************************
  End test code; begin custom script code.
******************************************************************************/
$(document).ready(function() {
  $('#selected-plays > li').addClass('horizontal');
  $('a[href^="mailto:"]').addClass('mailto');
  $('a[href$=".pdf"]').addClass('pdflink');
});
