<%@ codepage=65001%>
<% 
dim username,content
'POST 与 GET 区别在于Request.Form 和Request.QueryString的区别
username=request.Form("username")
content=request.Form("content")
response.Write("<div class='comment'><h6> "&username&" :</h6><p class='para'> "&content&" </p></div>")
 %>