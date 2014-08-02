<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="com.gqshao.commons.utils.ParameterUtils "%>
<%@ page import="java.util.Map"%>
<%
String forward = request.getParameter("forward_by_router");
if (StringUtils.isBlank(forward)) {
%>
<jsp:forward page="/WEB-INF/views/portal.jsp" />
<%
} else {
    forward = "/WEB-INF/views/" + forward + ".jsp";
    Map<String, Object> map = ParameterUtils.getParametersStartingWith(request, null);
    for (String key : map.keySet()) {
        if (!"forward".equals(key)) {
            request.setAttribute(key, map.get(key));
        }
    }
    RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
    dispatcher.forward(request, response);
}
%>
