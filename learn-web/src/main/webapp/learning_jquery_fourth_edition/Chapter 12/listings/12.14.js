/************************************************
  Set up table headings.
************************************************/
$(document).ready(function() {
  $('table').each(function(index) {
    var $table = $(this);
    $('<h3></h3>', {
      id: 'table-title-' + index,
      'class': 'table-title',
      text: 'Table ' + (index + 1),
      data: {'index': index},
      click: function(event) {
        event.preventDefault();
        $table.fadeToggle();
      },
      css: {glowColor: '#00ff00'}
    }).insertBefore($table);
  });
});

/************************************************
  Table 1: Sorting by parsing cell contents.
************************************************/
$(document).ready(function() {
  var $table1 = $('#t-1');
  var $headers = $table1.find('thead th').slice(1);
  $headers
    .each(function() {
      var keyType = this.className.replace(/^sort-/,'');
      $(this).data('keyType', keyType);
    })
    .wrapInner('<a href="#"></a>')
    .addClass('sort');

  var sortKeys = {
    alpha: function($cell) {
      var key = $cell.find('span.sort-key').text() + ' ';
      key += $.trim($cell.text()).toUpperCase();
      return key;
    },
    numeric: function($cell) {
      var num = $cell.text().replace(/^[^\d.]*/, '');
      var key = parseFloat(num);
      if (isNaN(key)) {
        key = 0;
      }
      return key;
    },
    date: function($cell) {
      var key = Date.parse('1 ' + $cell.text());
      return key;
    }
  };

  $headers.on('click', function(event) {
    event.preventDefault();
    var $header = $(this),
        column = $header.index(),
        keyType = $header.data('keyType'),
        sortDirection = 1;

    if ( !$.isFunction(sortKeys[keyType]) ) {
      return;
    }

    if ($header.hasClass('sorted-asc')) {
      sortDirection = -1;
    }

    var rows = $table1.find('tbody > tr').each(function() {
      var $cell = $(this).children('td').eq(column);
      $(this).data('sortKey', sortKeys[keyType]($cell));
    }).get();

    rows.sort(function(a, b) {
      var keyA = $(a).data('sortKey');
      var keyB = $(b).data('sortKey');
      if (keyA < keyB) return -sortDirection;
      if (keyA > keyB) return sortDirection;
      return 0;
    });

    $headers.removeClass('sorted-asc sorted-desc');
    $header.addClass(sortDirection == 1 ? 'sorted-asc' : 'sorted-desc');

    $.each(rows, function(index, row) {
      $table1.children('tbody').append(row);
    });
  });
});

/************************************************
  Table 2: Sorting by reading HTML5 data.
************************************************/
$(document).ready(function() {
  var $table2 = $('#t-2');
  var $headers = $table2.find('thead th').slice(1);
  $headers
    .wrapInner('<a href="#"></a>')
    .addClass('sort');

  var rows = $table2.find('tbody > tr').get();

  $headers.on('click', function(event) {
    event.preventDefault();
    var $header = $(this),
        sortKey = $header.data('sort').key,
        sortDirection = 1;

    if ($header.hasClass('sorted-asc')) {
      sortDirection = -1;
    }

    rows.sort(function(a, b) {
      var keyA = $(a).data('book')[sortKey];
      var keyB = $(b).data('book')[sortKey];

      if (keyA < keyB) return -sortDirection;
      if (keyA > keyB) return sortDirection;
      return 0;
    });

    $headers.removeClass('sorted-asc sorted-desc');
    $header.addClass(sortDirection == 1 ? 'sorted-asc' : 'sorted-desc');

    $.each(rows, function(index, row) {
      $table2.children('tbody').append(row);
    });
  });
});

/************************************************
  Table 3: Sorting by recreating HTML from JSON.
************************************************/
(function($) {
  function buildRow(row) {
    var html = '<tr>';
      html += '<td><img src="images/' + row.img + '"></td>';
      html += '<td>' + row.titleFormatted + '</td>';
      html += '<td>' + row.authorsFormatted + '</td>';
      html += '<td>' + row.published + '</td>';
      html += '<td>$' + row.price + '</td>';
    html += '</tr>';

    return html;
  }

  function buildRows(rows) {
    var allRows = $.map(rows, buildRow);
    return allRows.join('');
  }

  function prepRows(rows) {
    $.each(rows, function(i, row) {
      var authors = [],
          authorsFormatted = [];

      rows[i].titleFormatted = row.title;
      rows[i].title = row.title.toUpperCase();

      $.each(row.authors, function(j, auth) {
        authors[j] = auth.last_name + ' ' + auth.first_name;
        authorsFormatted[j] = auth.first_name + ' ' + auth.last_name;
      });
      rows[i].authorsFormatted = authorsFormatted.join(', ');
      rows[i].authors = authors.join(' ').toUpperCase();
    });

    return rows;
  }

  $.getJSON('books.json', function(json) {
    $(document).ready(function() {
      var $table3 = $('#t-3');
      var rows = prepRows(json);
      $table3.find('tbody').html(buildRows(rows));

      var $headers = $table3.find('thead th').slice(1);
      $headers
        .wrapInner('<a href="#"></a>')
        .addClass('sort');

      $headers.on('click', function(event) {
        event.preventDefault();
        var $header = $(this),
            sortKey = $header.data('sort').key,
            sortDirection = 1;

        if ($header.hasClass('sorted-asc')) {
          sortDirection = -1;
        }

        rows.sort(function(a, b) {
          var keyA = a[sortKey];
          var keyB = b[sortKey];

          if (keyA < keyB) return -sortDirection;
          if (keyA > keyB) return sortDirection;
          return 0;
        });

        $headers.removeClass('sorted-asc sorted-desc');
        $header.addClass(sortDirection == 1 ? 'sorted-asc' : 'sorted-desc');

        $table3.children('tbody').html(buildRows(rows));
      });
    });
  });
})(jQuery);
