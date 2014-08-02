<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.springframework.web.util.WebUtils" %>
<%@ page import="java.util.Map" %>
<%
    String forward = request.getParameter("forward_by_router");

    if (StringUtils.isBlank(forward)) {
%>
<jsp:forward page="/WEB-INF/views/bootstrap/index.jsp"/>
<%
    } else {
        // Forward 映射(转向)
        forward = "/WEB-INF/views/" + forward + ".jsp";
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
        for (String key : map.keySet()) {
            if (!"forward".equals(key)) {
                request.setAttribute(key, map.get(key));
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }
%>
