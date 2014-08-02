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
  :column()
  Select all table cells in the same column as the one specified.
******************************************************************************/
(function($) {
  $.fn.column = function() {
    var $cells = $();
    this.each(function() {
      var $td = $(this).closest('td, th');
      if ($td.length) {
        var colNum = $td[0].cellIndex + 1;
        var $columnCells = $td
          .closest('table')
          .find('td, th')
          .filter(':nth-child(' + colNum + ')');
        $cells = $cells.add($columnCells);
      }
    });
    return this.pushStack($cells);
  };
})(jQuery);


/******************************************************************************
  End plugin code; begin custom script code.
******************************************************************************/

$(document).ready(function() {
  function stripe() {
    $('#news')
      .find('tr.alt').removeClass('alt').end()
      .find('tbody').each(function() {
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

  $('#release').nextAll().addBack().addClass('highlight');

  $('#news td').click(function() {
    $('#news td.active').removeClass('active');
    $(this).column().addClass('active');
  });
});
