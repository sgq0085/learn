$(document).ready(function() {
  // Use attr() to add an id, rel, and title.
  $('div.chapter a').attr({
    rel: 'external',
    title: 'Learn more at Wikipedia',
    id: function(index, oldValue) {
      return 'wikilink-' + index;
    }
  });
});
