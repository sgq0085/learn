<%@ codepage=65001%>
<% 
dim username,content
username=request.Form("username")
content=request.Form("content")
'POST 与 GET 区别在于Request.Form 和Request.QueryString的区别
Response.ContentType="application/xml"
Response.Charset="utf-8" 
Response.Write("<?xml version=""1.0"" encoding=""utf-8""?>")
response.Write("<comments>")
response.Write("<comment username='"&username&"'>")
response.Write("<content>"&content&"</content>")
response.Write("</comment>")
response.write ("</comments>")

'直接输出为XML文件
'With Response 
'     .ContentType = "text/XML" 
'     .write("<?xml version=""1.0"" encoding=""utf-8""?>") 
'	  .write("<comments>")
'     .write("<comment username='"&username&"'>") 
'     .write("<content>"&content&"</content>") 
'     .write("</comment>") 
'	 .write("</comments>")
'End with
 %>