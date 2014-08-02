/*
THIS FILE IS FOR PERFORMANCE TESTING OF THE SORTING AND INSERTION PORTIONS OF CHAPTER 12 CODE
http://jsperf.com/sort-and-insert/2
*/
// prep code
var $table1 = $('#t-1'),
    $table2 = $('#t-2'),
    $table3 = $('#t-3'),
    $headers1 = $table1.find('thead th').slice(1),
    $headers2 = $table2.find('thead th').slice(1),
    $headers3 = $table3.find('thead th').slice(1),
    $tbodyRows1 = $table1.find('tbody tr'),
    $tbodyRows2 = $table2.find('tbody tr'),
    $tbodyRows3 = $table3.find('tbody tr'),
    tableData = [{"img":"2862_OS.jpg","title":"Drupal 7","authors":[{"first_name":"David","last_name":"Mercer"}],"published":"September 2010","price":"44.99","publishedYM":"2010-09"},{"img":"1847194141.jpg","title":"Object-Oriented JavaScript","authors":[{"first_name":"Stoyan","last_name":"Stefanov"}],"published":"July 2008","price":"39.99","publishedYM":"2008-07"},{"img":"0042_MockupCover_0.jpg","title":"jQuery 1.4 Reference Guide","authors":[{"first_name":"Karl","last_name":"Swedberg"},{"first_name":"Jonathan","last_name":"Chaffer"}],"published":"January 2010","price":"39.99","publishedYM":"2010-01"},{"img":"0386OT_Cocoa%20and%20OBjective-C%20Cookbookcov.jpg","title":"Cocoa and Objective-C Cookbook","authors":[{"first_name":"Jeff","last_name":"Hawkins"}],"published":"May 2011","price":"39.99","publishedYM":"2011-05"},{"img":"4668_Python%20Testing%20Cookbook.jpg","title":"Python Testing Cookbook","authors":[{"first_name":"Greg L.","last_name":"Turnquist"}],"published":"May 2011","price":"44.99","publishedYM":"2011-05"},{"img":"3760OS_Linux%20Shell%20Scripting%20Cookbook.jpg","title":"Linux Shell Scripting Cookbook","authors":[{"first_name":"Sarath","last_name":"Lakshman"}],"published":"January 2011","price":"44.99","publishedYM":"2011-01"},{"img":"4965OS_Nginx%201%20Web%20Server%20Implementation%20Cookbook.jpg","title":"Nginx 1 Web Server Implementation Cookbook","authors":[{"first_name":"Dipankar","last_name":"Sarkar"}],"published":"May 2011","price":"39.99","publishedYM":"2011-05"},{"img":"1048OT_HTML5%20Multimedia%20Development%20Cookbookcov.jpg","title":"HTML5 Multimedia Development Cookbookcov.jpg","authors":[{"first_name":"Dale","last_name":"Cruse"},{"first_name":"Lee","last_name":"Jordan"}],"published":"May 2011","price":"39.99","publishedYM":"2011-05"},{"img":"0942OT_Core%20Data%20Essentials_0.jpg","title":"Core Data Essentials.jpg","authors":[{"first_name":"B.M.","last_name":"Harwani"}],"published":"April 2011","price":"44.99","publishedYM":"2011-04"},{"img":"3524OS_WordPress%203%20Plugin%20Development%20Essentials_0.jpg","title":"WordPress 3 Plugin Development Essentials","authors":[{"first_name":"Brian","last_name":"Bondari"},{"first_name":"Everett","last_name":"Griffiths"}],"published":"March 2011","price":"39.99","publishedYM":"2011-03"},{"img":"9867_Latex%20cov.jpg","title":"LaTeX Beginner's Guide","authors":[{"first_name":"Stefan","last_name":"Kottwitz"}],"published":"March 2011","price":"44.99","publishedYM":"2011-03"},{"img":"2923OS_Panda3D%20game%20developer%E2%80%99s%20cookbook.jpg","title":"Panda3D 1.7 Game Developer's Cookbook","authors":[{"first_name":"Christoph","last_name":"Lang"}],"published":"March 2011","price":"44.99","publishedYM":"2011-03"},{"img":"1926_Cake%20PHP%201.3cov.jpg","title":"CakePHP 1.3 Application Development Cookbook","authors":[{"first_name":"Mariano","last_name":"Iglesias"}],"published":"March 2011","price":"39.99","publishedYM":"2011-03"},{"img":"4804os_mockupcover_ex.jpg","title":"Magento 1.4 Themes Design","authors":[{"first_name":"Richard","last_name":"Carter"}],"published":"January 2011","price":"39.99","publishedYM":"2011-01"},{"img":"0349OS_MockupCover_0.jpg","title":"Django JavaScript Integration: AJAX and jQuery","authors":[{"first_name":"Jonathan","last_name":"Hayward"}],"published":"January 2011","price":"44.99","publishedYM":"2011-01"},{"img":"1445OS_MockupCover_Magento_1.4_Development_Cookbook_cb.jpg","title":"Magento 1.4 Development Cookbook","authors":[{"first_name":"Nurul","last_name":"Ferdous"}],"published":"December 2010","price":"44.99","publishedYM":"2010-12"}];

function prepRows(json) {
  $.each(json, function(i, row) {
    var authorsFormatted = [],
        authorSortKey = [];
    $.each(row.authors, function(j, auth) {
      authorSortKey[j] = auth.last_name + ' ' + auth.first_name;
      authorsFormatted[j] = auth.first_name + ' ' + auth.last_name;
    });
    json[i].authors = authorSortKey.join(' ');
    json[i].authorsFormatted = authorsFormatted.join(' ');
  });
  return json;
}
var rows2 = $table2.find('tbody > tr').get();
var rows3 = prepRows(tableData);
$headers1.each(function() {
  var keyType = this.className.replace(/^sort-/,'');
  $(this).data('keyType', keyType);
});
var sortKeys1 = {
  alpha: function($cell) {
    var key = $cell.find('span.sort-key').text() + ' ';
    key += $cell
      .text().replace(/^\s*(.*)\s*$/, function(str, match1) {
        return match1.toUpperCase();
      });
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
tries = 0;
fails = 0;
failedAt = [];

/** =setup
************************************************************/
$table1.find('tbody').empty().append($tbodyRows1);
$table2.find('tbody').empty().append($tbodyRows2);
$table3.find('tbody').empty().append($tbodyRows3);

/** =teardown
************************************************************/
tries++;
var success = false;
$('tbody tr:first-child').each(function() {
  if ($(this).text().indexOf('Press 3 Plugin Development Essentials') !== -1) {
    success = true;
  }
});
if (!success) {
  failedAt.push(tries);
  fails++;
  $('#errors').html(fails + '<br>' + failedAt.join(', '));
}
/** =tests
************************************************************/


// test 1
(function() {


  var column = $headers1.eq(1),
      keyType = $headers1.eq(1).data('keyType'),
      sortDirection = 1;

  var rows1 = $table1.find('tbody > tr').each(function() {
    var $cell = $(this).children('td').eq(column);
    $(this).data('sortKey', sortKeys1[keyType]($cell));
  }).get();

  rows1.sort(function(a, b) {
    var keyA = $(a).data('sortKey');
    var keyB = $(b).data('sortKey');
    if (keyA < keyB) {
      return -sortDirection;
    }
    if (keyA > keyB) {
      return sortDirection;
    }
    return 0;
  });

  $.each(rows1, function(index, row) {
    $table1.children('tbody').append(row);
  });

})();

// test 2
(function() {
  var sortInfo = $headers2.eq(1).data('sort'),
      sortKey = sortInfo.key,
      sortDirection = 1;

  rows2.sort(function(a, b) {
    var keyA = $(a).data('book')[sortKey];
    var keyB = $(b).data('book')[sortKey];

    if (keyA < keyB) {
      return -sortDirection;
    }
    if (keyA > keyB) {
      return sortDirection;
    }
    return 0;
  });
  $.each(rows2, function(index, row) {
    $table2.children('tbody').append(row);
  });
})();

// test 3a
(function() {
  sortRows(rows3);
  function buildRow(row) {

    var html = '<tr>';
      html += '<td></td>';
      html += '<td>' + row.title + '</td>';
      html += '<td>' + row.authorsFormatted + '</td>';
      html += '<td>' + row.published + '</td>';
      html += '<td>$' + row.price + '</td>';
    html += '</tr>';

    return html;
  }

  function buildRows(json) {
    var allRows = [];
    $.each(json, function(index, row) {
      allRows[index] = buildRow(row);
    });

    return allRows.join('');
  }


  function sortRows(rows) {

    var sortInfo = $headers3.eq(1).data('sort'),
        sortKey = sortInfo.key,
        sortDirection = 1;

    rows.sort(function(a, b) {
      var keyA = a[sortKey];
      var keyB = b[sortKey];

      if (keyA < keyB) {
        return -sortDirection;
      }
      if (keyA > keyB) {
        return sortDirection;
      }
      return 0;
    });

    $table3.children('tbody').html( buildRows(rows) );

  }
})();

// test 3b
(function() {
  sortRows(rows3);
  function buildRows(json) {
    var allRows = [];
    $.each(json, function(index, row) {

      var html = '<tr>';
        html += '<td></td>';
        html += '<td>' + row.title + '</td>';
        html += '<td>' + row.authorsFormatted + '</td>';
        html += '<td>' + row.published + '</td>';
        html += '<td>$' + row.price + '</td>';
      html += '</tr>';

      allRows[index] = html;
    });

    return allRows.join('');
  }

  function sortRows(rows) {

    var sortInfo = $headers3.eq(1).data('sort'),
        sortKey = sortInfo.key,
        sortDirection = 1;

    rows.sort(function(a, b) {
      var keyA = a[sortKey];
      var keyB = b[sortKey];

      if (keyA < keyB) {
        return -sortDirection;
      }
      if (keyA > keyB) {
        return sortDirection;
      }
      return 0;
    });

    $table3.children('tbody').html( buildRows(rows) );

  }
})();
