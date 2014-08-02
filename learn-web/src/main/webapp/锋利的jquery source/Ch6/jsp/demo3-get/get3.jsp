<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String username = request.getParameter("username");
  String content = request.getParameter("content");
  out.println("{ \"username\" : '"+username+"' , \"content\" : '"+content+"'}");
%>