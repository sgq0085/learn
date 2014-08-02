<%@ codepage=65001%>
<% 
dim username,content
username=request.Form("username")
content=request.Form("content")
'POST 与 GET 区别在于Request.Form 和Request.QueryString的区别
response.Write("{ \"username\" : '"&username&"' , \"content\" : '"&content&"'}")

 %>