/******************************************************************************
  Our plugin code comes first in this document. Normally, plugins would
  appear in separate files named jquery.plugin-name.js, but for our examples
  it's convenient to place this plugin code in the same JavaScript file as
  the code that calls it.
******************************************************************************/

/******************************************************************************
  $.sum()
  Return the total of the numeric values in an array/object.
******************************************************************************/
(function($) {
  $.mathUtils = {
    sum: function(array) {
      var total = 0;

      $.each(array, function(index, value) {
        value = $.trim(value);
        value = parseFloat(value) || 0;

        total += value;
      });
      return total;
    },
    average: function(array) {
      if ($.isArray(array)) {
        return $.mathUtils.sum(array) / array.length;
      }
      return '';
    }
  };
})(jQuery);


/******************************************************************************
  .swapClass()
  Exchange one class for another on the selected elements.
******************************************************************************/
(function($) {
  $.fn.swapClass = function(class1, class2) {
    return this.each(function() {
      var $element = $(this);
      if ($element.hasClass(class1)) {
        $element.removeClass(class1).addClass(class2);
      }
      else if ($element.hasClass(class2)) {
        $element.removeClass(class2).addClass(class1);
      }
    });
  };
})(jQuery);


/******************************************************************************
  .shadow()
  Create a shadow effect on any element by brute-force copying.
******************************************************************************/
(function($) {
  $.fn.shadow = function() {
    return this.each(function() {
      var $originalElement = $(this);
      for (var i = 0; i < 5; i++) {
        $originalElement
          .clone()
          .css({
            position: 'absolute',
            left: $originalElement.offset().left + i,
            top: $originalElement.offset().top + i,
            margin: 0,
            zIndex: -1,
            opacity: 0.1
          })
          .appendTo('body');
      }
    });
  };
})(jQuery);


/******************************************************************************
  End plugin code; begin custom script code.
******************************************************************************/
$(document).ready(function() {
  var $inventory = $('#inventory tbody');
  var quantities = $inventory.find('td:nth-child(2)')
  .map(function(index, qty) {
    return $(qty).text();
  }).get();

  var prices = $inventory.find('td:nth-child(3)')
  .map(function(index, qty) {
    return $(qty).text();
  }).get();

  var sum = $.mathUtils.sum(quantities);
  var average = $.mathUtils.average(prices);
  $('#sum').find('td:nth-child(2)').text(sum);
  $('#average').find('td:nth-child(3)').text(average.toFixed(2));

  $('table').click(function() {
    $('tr').swapClass('one', 'two');
  });

  $('h1').shadow();
});
