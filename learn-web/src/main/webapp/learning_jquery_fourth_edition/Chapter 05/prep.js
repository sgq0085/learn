$(document).ready(function() {
  $('a').each(function() {
    if (this.hostname == location.hostname && this.hash) {
      this.href = location.href + this.hash;
    }
  });
});