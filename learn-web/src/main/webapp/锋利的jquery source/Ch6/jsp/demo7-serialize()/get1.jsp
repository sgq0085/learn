<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  request.setCharacterEncoding("UTF-8");
  String username = request.getParameter("username");
  String content = request.getParameter("content");
  out.println("<div class='comment'><h6> "+username+" :</h6><p class='para'> "+content+" </p></div>");
%>