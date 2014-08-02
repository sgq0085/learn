var comments = [
  {
    "username": "张三",
    "content": "沙发."
  },
  {
    "username": "李四",
    "content": "板凳."
  },
  {
    "username": "王五",
    "content": "地板."
  }
];

  var html = '';
  $.each( comments , function(commentIndex, comment) {
      html += '<div class="comment"><h6>' + comment['username'] + ':</h6><p class="para">' + comment['content'] + '</p></div>';
  })

  $('#resText').html(html);
