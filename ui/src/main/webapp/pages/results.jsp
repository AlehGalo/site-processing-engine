<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h2>Number of records ${count}</h2>
	<c:choose>
		<c:when test="${not empty lists}">
			<c:forEach var="listValue" items="${lists}">
				${listValue}<br />
			</c:forEach>
		</c:when>
		<c:otherwise>
			<h5>No Data</h5>
		</c:otherwise>
	</c:choose>
</body>
</html>