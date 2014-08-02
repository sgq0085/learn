<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  request.setCharacterEncoding("UTF-8");
  String username = request.getParameter("username");
  String content = request.getParameter("content");
  response.setContentType("text/xml");
  out.println("<?xml version='1.0' encoding='UTF-8'?>");
  out.println("<comments>");
  out.println("<comment username='"+username+"'>");
  out.println("<content>"+content+"</content>");
  out.println("</comment>");
  out.println("</comments>");
%>