<%@ codepage=65001%>
<% 
	dim username,content
	username=request("username")
	content=request("content")

	response.Write("{ \"username\" : '"&username&"' , \"content\" : '"&content&"'}")

%>