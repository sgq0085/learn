<%@ codepage=65001%>
<% 
dim username,content
username=request.QueryString("username")
content=request.QueryString("content")
response.Write("<div class='comment'><h6> "&username&" :</h6><p class='para'> "&content&" </p></div>")
 %>