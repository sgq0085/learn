<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%
        // Redirect 重定向
        // String site = request.getContextPath()+ "/?forward_by_router=commons/test1_init";
        // response.setStatus(response.SC_MOVED_TEMPORARILY);
        // response.setHeader("Location", site);

        // response中将setStatus 和 setHeader合并为sendRedirect
        response.sendRedirect(request.getContextPath() + "/?forward_by_router=commons/test1_init");
    %>
</body>
</html>