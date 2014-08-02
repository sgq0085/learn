<?php
$ajax = isset($_SERVER['HTTP_X_REQUESTED_WITH']) &&
        strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) == 'xmlhttprequest';

$req_term = isset($_REQUEST['term']) ? $_REQUEST['term'] : '';
if (!$ajax) {
?>
  <!DOCTYPE HTML>
  <html lang="en">
  <head>
    <meta charset="utf-8">
    <title>The Devil's Dictionary by Ambrose Bierce</title>
    <link rel="stylesheet" href="06.css">
  </head>
  <body>
    <div id="container">
    <div id="header">
      <h2>The Devil's Dictionary</h2>
      <div class="author">by Ambrose Bierce</div>
    </div>

    <form action="f.php">
      <input type="text" name="term" value="<?= $req_term; ?>" id="term" />
      <button type="submit">Search</button>
    </form>

<?php
}
$entries = array(
  'FAITH' => array(
    'part' => 'n.',
    'definition' => 'Belief without evidence in what is told by one who speaks without knowledge, of things without parallel.',
  ),
  'FAMOUS' => array(
    'part' => 'adj.',
    'definition' => 'Conspicuously miserable.',
    'quote' => array(
      'Done to a turn on the iron, behold',
      'Him who to be famous aspired.',
      'Content?  Well, his grill has a plating of gold,',
      'And his twistings are greatly admired.',
    ),
    'author' => 'Hassan Brubuddy',
  ),
  'FELON' => array(
    'part' => 'n.',
    'definition' => 'A person of greater enterprise than discretion, who in embracing an opportunity has formed an unfortunate attachment.',
  ),
  'FIDDLE' => array(
    'part' => 'n.',
    'definition' => 'An instrument to tickle human ears by friction of a horse\'s tail on the entrails of a cat.',
    'quote' => array(
      'To Rome said Nero:  "If to smoke you turn',
      'I shall not cease to fiddle while you burn."',
      'To Nero Rome replied:  "Pray do your worst,',
      '\'Tis my excuse that you were fiddling first."',
    ),
    'author' => 'Orm Pludge',
  ),
  'FIDELITY' => array(
    'part' => 'n.',
    'definition' => 'A virtue peculiar to those who are about to be betrayed.',
  ),
  'FLOP' => array(
    'part' => 'v.',
    'definition' => 'Suddenly to change one\'s opinions and go over to another party.  The most notable flop on record was that of Saul of Tarsus, who has been severely criticised as a turn-coat by some of our partisan journals.',
  ),
  'FORCE' => array(
    'part' => 'n.',
    'definition' => '',
    'quote' => array(
      '"Force is but might," the teacher said &mdash;',
      '"That definition\'s just."',
      'The boy said naught but thought instead,',
      'Remembering his pounded head:',
      '"Force is not might but must!"',
    ),
  ),
  'FORGETFULNESS' => array(
    'part' => 'n.',
    'definition' => 'A gift of God bestowed upon doctors in compensation for their destitution of conscience.',
  ),
  'FRIENDLESS' => array(
    'part' => 'adj.',
    'definition' => 'Having no favors to bestow.  Destitute of fortune. Addicted to utterance of truth and common sense.',
  ),
  'FRIENDSHIP' => array(
    'part' => 'n.',
    'definition' => 'A ship big enough to carry two in fair weather, but only one in foul.',
    'quote' => array(
      'The sea was calm and the sky was blue;',
      'Merrily, merrily sailed we two.',
      '(High barometer maketh glad.)',
      'On the tipsy ship, with a dreadful shout,',
      'The tempest descended and we fell out.',
      '(O the walking is nasty bad!)',
    ),
    'author' => 'Armit Huff Bettle',
  ),
  'FUTURE' => array(
    'part' => 'n.',
    'definition' => 'That period of time in which our affairs prosper, our friends are true and our happiness is assured.',
  ),
);

$output = array();
foreach ($entries as $term => $entry) {
  if (strpos($term, strtoupper($_REQUEST['term'])) !== FALSE) {
    $output[] = build_entry($term, $entry);
  }
}

if (!empty($output)) {
  echo implode("\n", $output);
} else {
  echo '<div class="entry">Sorry, no entries found for ';
  echo '<strong>' . $_REQUEST['term'] . '</strong>.';
  echo '</div>';
}


function build_entry($term, $entry) {
  $html = '<div class="entry">';
  $html .= '<h3 class="term">';
  $html .= $term;
  $html .= '</h3>';

  $html .= '<div class="part">';
  $html .= $entry['part'];
  $html .= '</div>';

  $html .= '<div class="definition">';
  $html .= $entry['definition'];
  if (isset($entry['quote'])) {
    foreach ($entry['quote'] as $line) {
      $html .= '<div class="quote-line">'. $line .'</div>';
    }
    if (isset($entry['author'])) {
      $html .= '<div class="quote-author">'.
        $entry['author'] .'</div>';
    }
  }
  $html .= '</div>';

  $html .= '</div>';
  return $html;
}

if (!$ajax) {
?>
    </div>
  </body>
</html>
<?php } ?>