/******************************************************************************
  Our plugin code comes first in this document. Normally, plugins would
  appear in separate files named jquery.plugin-name.js, but for our examples
  it's convenient to place this plugin code in the same JavaScript file as
  the code that calls it.
******************************************************************************/


/******************************************************************************
  :group()
  Select n elements, skip n elements, etc.
******************************************************************************/
(function($) {
  $.extend($.expr[':'], {
    group: function(element, index, matches, set) {
      var num = parseInt(matches[3], 10);
      if (isNaN(num)) {
        return false;
      }
      return index % (num * 2) < num;
    }
  });
})(jQuery);


/******************************************************************************
  End plugin code; begin custom script code.
******************************************************************************/

$(document).ready(function() {
  function stripe() {
    $('#news').find('tr.alt').removeClass('alt');
    $('#news tbody').each(function() {
      $(this).children(':visible').has('td')
        .filter(':group(3)').addClass('alt');
    });
  }
  stripe();

  $('#topics a').click(function(event) {
    event.preventDefault();
    var topic = $(this).text();

    $('#topics a.selected').removeClass('selected');
    $(this).addClass('selected');

    $('#news').find('tr').show();
    if (topic != 'All') {
      $('#news').find('tr:has(td)').not(function() {
        return $(this).children(':nth-child(4)').text() == topic;
      }).hide();
    }
    stripe();
  });

  var $cell = $('#release');

  $cell.addClass('highlight');
  console.log($cell.context);
  console.log($cell.selector);
  console.log($cell.prevObject);
});
