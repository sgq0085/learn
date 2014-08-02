var entries = [
  {
    "term": "CALAMITY",
    "part": "n.",
    "definition": "A more than commonly plain and unmistakable reminder that the affairs of this life are not of our own ordering.  Calamities are of two kinds:  misfortune to ourselves, and good fortune to others."
  },
  {
    "term": "CANNIBAL",
    "part": "n.",
    "definition": "A gastronome of the old school who preserves the simple tastes and adheres to the natural diet of the pre-pork period."
  },
  {
    "term": "CHILDHOOD",
    "part": "n.",
    "definition": "The period of human life intermediate between the idiocy of infancy and the folly of youth &mdash; two removes from the sin of manhood and three from the remorse of age."
  },
  {
    "term": "CLARIONET",
    "part": "n.",
    "definition": "An instrument of torture operated by a person with cotton in his ears.  There are two instruments that are worse than a clarionet &mdash; two clarionets."
  },
  {
    "term": "COMFORT",
    "part": "n.",
    "definition": "A state of mind produced by contemplation of a neighbor's uneasiness."
  },
  {
    "term": "CORSAIR",
    "part": "n.",
    "definition": "A politician of the seas."
  }
];

var html = '';

$.each(entries, function() {
  html += '<div class="entry">';
  html += '<h3 class="term">' + this.term + '</h3>';
  html += '<div class="part">' + this.part + '</div>';
  html += '<div class="definition">' + this.definition + '</div>';
  html += '</div>';
});

$('#dictionary').html(html);
