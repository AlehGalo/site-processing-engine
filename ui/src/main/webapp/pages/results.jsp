<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h2>Scanned resources</h2>

	<c:choose>
		<c:when test="${not empty lists}">
			<ul>
				<c:forEach var="listValue" items="${lists}">
					<li>${listValue}</li>
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<h5>No Data</h5>
		</c:otherwise>
	</c:choose>
</body>
</html>