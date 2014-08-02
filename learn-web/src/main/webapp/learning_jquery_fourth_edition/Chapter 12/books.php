<?php
$type = 'server';
$format = !isset($_GET['debug']);

if ( isset($_SERVER['HTTP_X_REQUESTED_WITH']) ):
  $type = 'json';
  header('Content-type: application/json');
elseif ( isset($_GET['callback']) ):
  $type = 'jsonp';
  header('Content-type: text/javascript');
endif;


$books_output = get_books($type, $format);

echo $books_output;

function format_books($books) {
  $table_num = 2;

  $table = '<table class="sortable">';

  if ($table_num > 1):
    $thead = array(
      '<thead>',
        '<tr>',
          '<th></th>',
          '<th data-sort=\'' . json_encode( array('key' => 'title') ) . '\'>Title</th>',
          '<th data-sort=\'' . json_encode( array('key' => 'authors') ) . '\'>Author(s)</th>',
          '<th data-sort=\'' . json_encode( array('key' => 'publishedYM') ) . '\'>Publish Date</th>',
          '<th data-sort=\'' . json_encode( array('key' => 'price') ) . '\'>Price</th>',
        '</tr>',
      '</thead>',
      "\n",
    );
  else:
    $thead = array(
      '<thead>',
        '<tr>',
          '<th>&nbsp;</th>',
          '<th>Title</th>',
          '<th>Author(s)</th>',
          '<th>Publish Date</th>',
          '<th>Price</th>',
        '</tr>',
      '</thead>',
      "\n",
    );
  endif;

  $rows = array('<tbody>');
  foreach ($books as $book) :

    $row_data = '';

    // prep
    $title = $book['title'];
    $authors = array();
    $author_data = array();
    foreach ($book['authors'] as $i => $author):
      $authors[] = $author['first_name'] . ' ' . ($i == 0 && $table_num == 1 ? '<span class="sort-key">' . $author['last_name'] . '</span>' : $author['last_name']);
      $author_data[] = strtoupper($author['last_name']) . ' ' . strtoupper($author['first_name']);
    endforeach;

    if ($table_num = 2):
      $book['title'] = strtoupper($title);
      $book['authors'] = implode(' ' , $author_data);
      $row_data .= ' data-book=\'';
      $row_data .= json_encode($book);
      $row_data .= '\'';
    endif;

    $rows[] = '<tr' . $row_data . '>';
      // image
      $rows[] = '<td><img src="images/' . $book['img'] . '" alt="' . $title . '">';

      $rows[] = '</td>';

      // title
      $rows[] = '<td>' . $title . '</td>';

      // authors
      $rows[] = '<td>'. implode(', ', $authors) . '</td>';

      // publish date
      $rows[] = '<td>' . $book['published'] . '</td>';

      // price
      $rows[] = '<td>' . '$' . $book['price'] . '</td>';

    $rows[] = '</tr>';
  endforeach;
  $rows[] = '</tbody>';

  $table .= "\n" . implode("\n", $thead) . implode("\n", $rows) . "\n" . '</table>';
  return $table;
}



function get_books($request_type='server', $format) {

  $books = array(
    array(
      'img' => '2862_OS.jpg',
      'title' => 'Drupal 7',

      'authors' => array(
        array('first_name' => 'David', 'last_name' => 'Mercer'),
      ),
      'published' => 'September 2010',
      'price' => 44.99,
    ),
    array(
      'img' => '3685EN_Amazon SimpleDB_LITE_0.jpg',
      'title' => 'Amazon SimpleDB: LITE',
      'authors' => array(
        array('first_name' => 'Prabhakar', 'last_name' => 'Chaganti'),
        array('first_name' => 'Rich', 'last_name' => 'Helms'),
      ),
      'published' => 'May 2011',
      'price' => 9.99,
    ),
    array(
      'img' => '1847194141.jpg',
      'title' => 'Object-Oriented JavaScript',
      'authors' => array(
        array('first_name' => 'Stoyan', 'last_name' => 'Stefanov'),
      ),
      'published' => 'July 2008',
      'price' => 39.99,
    ),
    array(
      'img' => '0042_MockupCover_0.jpg',
      'title' => 'jQuery 1.4 Reference Guide',
      'authors' => array(
        array('first_name' => 'Karl', 'last_name' => 'Swedberg'),
        array('first_name' => 'Jonathan', 'last_name' => 'Chaffer'),
      ),
      'published' => 'January 2010',
      'price' => 39.99,
    ),
    array(
      'img' => '0386OT_Cocoa and OBjective-C Cookbookcov.jpg',
      'title' => 'Cocoa and Objective-C Cookbook',
      'authors' => array(
        array('first_name' => 'Jeff', 'last_name' => 'Hawkins'),
      ),
      'published' => 'May 2011',
      'price' => 39.99,
    ),
    array(
      'img' => '4668_Python Testing Cookbook.jpg',
      'title' => 'Python Testing Cookbook',
      'authors' => array(
        array('first_name' => 'Greg L.', 'last_name' => 'Turnquist'),
      ),
      'published' => 'May 2011',
      'price' => 44.99,
    ),
    array(
      'img' => '3760OS_Linux Shell Scripting Cookbook.jpg',
      'title' => 'Linux Shell Scripting Cookbook',
      'authors' => array(
        array('first_name' => 'Sarath', 'last_name' => 'Lakshman'),
      ),
      'published' => 'January 2011',
      'price' => 44.99,
    ),
    array(
      'img' => '4965OS_Nginx 1 Web Server Implementation Cookbook.jpg',
      'title' => 'Nginx 1 Web Server Implementation Cookbook',
      'authors' => array(
        array('first_name' => 'Dipankar', 'last_name' => 'Sarkar'),
      ),
      'published' => 'May 2011',
      'price' => 39.99,
    ),
    array(
      'img' => '1048OT_HTML5 Multimedia Development Cookbookcov.jpg',
      'title' => 'HTML5 Multimedia Development Cookbookcov.jpg',
      'authors' => array(
        array('first_name' => 'Dale', 'last_name' => 'Cruse'),
        array('first_name' => 'Lee', 'last_name' => 'Jordan'),
      ),
      'published' => 'May 2011',
      'price' => 39.99,
    ),
    array(
      'img' => '0942OT_Core Data Essentials_0.jpg',
      'title' => 'Core Data Essentials.jpg',
      'authors' => array(
        array('first_name' => 'B.M.', 'last_name' => 'Harwani'),
      ),
      'published' => 'April 2011',
      'price' => 44.99,
    ),
    array(
      'img' => '3524OS_WordPress 3 Plugin Development Essentials_0.jpg',
      'title' => 'WordPress 3 Plugin Development Essentials',
      'authors' => array(
        array('first_name' => 'Brian', 'last_name' => 'Bondari'),
        array('first_name' => 'Everett', 'last_name' => 'Griffiths'),
      ),
      'published' => 'March 2011',
      'price' => 39.99,
    ),
    array(
      'img' => '9867_Latex cov.jpg',
      'title' => 'LaTeX Beginner\'s Guide',
      'authors' => array(
        array('first_name' => 'Stefan', 'last_name' => 'Kottwitz'),
      ),
      'published' => 'March 2011',
      'price' => 44.99,
    ),
    array(
      'img' => '2923OS_Panda3D game developer’s cookbook.jpg',
      'title' => 'Panda3D 1.7 Game Developer\'s Cookbook',
      'authors' => array(
        array('first_name' => 'Christoph', 'last_name' => 'Lang'),
      ),
      'published' => 'March 2011',
      'price' => 44.99,
    ),
    array(
      'img' => '1926_Cake PHP 1.3cov.jpg',
      'title' => 'CakePHP 1.3 Application Development Cookbook',
      'authors' => array(
        array('first_name' => 'Mariano', 'last_name' => 'Iglesias'),
      ),
      'published' => 'March 2011',
      'price' => 39.99,
    ),
    array(
      'img' => '4804os_mockupcover_ex.jpg',
      'title' => 'Magento 1.4 Themes Design',
      'authors' => array(
        array('first_name' => 'Richard', 'last_name' => 'Carter'),
      ),
      'published' => 'January 2011',
      'price' => 39.99,
    ),
    array(
      'img' => '0349OS_MockupCover_0.jpg',
      'title' => 'Django JavaScript Integration: AJAX and jQuery',
      'authors' => array(
        array('first_name' => 'Jonathan', 'last_name' => 'Hayward'),
      ),
      'published' => 'January 2011',
      'price' => 44.99,
    ),
    array(
      'img' => '1445OS_MockupCover_Magento_1.4_Development_Cookbook_cb.jpg',
      'title' => 'Magento 1.4 Development Cookbook',
      'authors' => array(
        array('first_name' => 'Nurul', 'last_name' => 'Ferdous'),
      ),
      'published' => 'December 2010',
      'price' => 44.99,
    ),
    array(
      'img' => '',
      'title' => '',
      'authors' => array(
        array('first_name' => '', 'last_name' => ''),
      ),
      'published' => '',
      'price' => '',
    ),
    array(
      'img' => '',
      'title' => '',
      'authors' => array(
        array('first_name' => '', 'last_name' => ''),
      ),
      'published' => '',
      'price' => '',
    ),
    array(
      'img' => '',
      'title' => '',
      'authors' => array(
        array('first_name' => '', 'last_name' => ''),
      ),
      'published' => '',
      'price' => '',
    ),
    array(
      'img' => '',
      'title' => '',
      'authors' => array(
        array('first_name' => '', 'last_name' => ''),
      ),
      'published' => '',
      'price' => '',
    ),
    array(
      'img' => '',
      'title' => '',
      'authors' => array(
        array('first_name' => '', 'last_name' => ''),
      ),
      'published' => '',
      'price' => '',
    ),
    array(
      'img' => '',
      'title' => '',
      'authors' => array(
        array('first_name' => '', 'last_name' => ''),
      ),
      'published' => '',
      'price' => '',
    ),
    array(
      'img' => '',
      'title' => '',
      'authors' => array(
        array('first_name' => '', 'last_name' => ''),
      ),
      'published' => '',
      'price' => '',
    ),

  );

  $output = array();
  foreach ($books as $book) {
    if ($book['title']):
      $book['img'] = rawurlencode($book['img']);

      $d = strtotime($book['published']);
      $book['publishedYM'] = date('Y-m', $d);

      $output[] = $book;
    endif;
  }

  switch ($request_type) {
    case 'json':
      $output = json_encode($output);
      break;
    case 'jsonp':
      $output = $_GET['callback'] . '(' . json_encode($output) . ')';
      break;
    case 'server':
      $output = $format ? format_books($output) : $output;
      break;
    default:
      break;
  }

  return $output;
}


?>
