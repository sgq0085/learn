<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.gqshao.test.sys.rbac.domain.User" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<div align="center">
    <p>${header["user-agent"]}</p>
</div>
<c:forEach items="${data}" var="item">
    ${item.id} : ${item.loginName}<br/>
</c:forEach>
<c:forTokens items="Zara,nuha,roshy" delims="," var="name">
    <c:out value="${name}"/>
</c:forTokens>

<fmt:formatDate pattern="yyyy-MM-dd" value="${now}"/>
<fmt:parseDate value="${now}" var="parsedEmpDate" pattern="dd-MM-yyyy"/>
<c:out value="${parsedEmpDate}"/>

<c:set var="theString" value="I am a test String"/>
<c:if test="${fn:contains(theString, 'test')}">
    <p>Found test string ${pageContext.session }
    </p>
</c:if>
<br/> pageContext.session
<br/> pageContext.page ${pageContext.page }
<br/> pageContext.request ${pageContext.request.queryString }
<br/> pageContext.response ${pageContext.response }
<br/> pageContext.exception ${pageContext.exception }
<br/> pageContext.servletConfig ${pageContext.servletConfig }

</body>
</html>