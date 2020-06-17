<%@page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- početna stranica --%>

<html>
<style>
form {
	width: 100px;
	margin: 0 auto;
}
</style>
<body>
	<h1>Dostupne ankete za glasanje:</h1>
	<c:forEach var="poll" items="${polls}">
		<p>
			<a href="glasanje?pollID=${poll.getId()}">${poll.getTitle()}</a>
		</p>
	</c:forEach>
</body>
</html>