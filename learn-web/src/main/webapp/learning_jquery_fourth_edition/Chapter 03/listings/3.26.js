$(document).ready(function() {
  // Enable hover effect on the style switcher
  $('#switcher').hover(function() {
    $(this).addClass('hover');
  }, function() {
    $(this).removeClass('hover');
  });

  // Allow the style switcher to expand and collapse.
  var toggleSwitcher = function(event) {
    if (!$(event.target).is('button')) {
      $('#switcher button').toggleClass('hidden');
    }
  };
  $('#switcher').on('click', toggleSwitcher);

  // Simulate a click so we start in a collaped state.
  $('#switcher').click();

  // The setBodyClass() function changes the page style.
  // The style switcher state is also updated.
  var setBodyClass = function(className) {
    $('body').removeClass().addClass(className);

    $('#switcher button').removeClass('selected');
    $('#switcher-' + className).addClass('selected');

    $('#switcher').off('click', toggleSwitcher);

    if (className == 'default') {
      $('#switcher').on('click', toggleSwitcher);
    }
  };

  // begin with the switcher-default button "selected"
  $('#switcher-default').addClass('selected');

  // Map key codes to their corresponding buttons to click
  var triggers = {
    D: 'default',
    N: 'narrow',
    L: 'large'
  };

  // Call setBodyClass() when a button is clicked.
  $('#switcher').click(function(event) {
    if ($(event.target).is('button')) {
      var bodyClass = event.target.id.split('-')[1];
      setBodyClass(bodyClass);
    }
  });

  // Call setBodyClass() when a key is pressed.
  $(document).keyup(function(event) {
    var key = String.fromCharCode(event.which);
    if (key in triggers) {
      setBodyClass(triggers[key]);
    }
  });
});
