<%@ page contentType="text/html; charset=UTF-8" session="true"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<style>
h1 {
	text-align: center;
}

label {
 	font-family: Verdana, Arial, Helvetica, sans-serif;
	 font-size: medium;
}
</style>

<%
	Object currentNick = session.getAttribute("current.user.nick");
	if (currentNick != null) {
		request.setAttribute("nick", currentNick);
	}
%>

<c:choose>
	<c:when test="${nick != null}">
		<div align="center">
			<form action="logout">
				<label for="name"> <% out.print((String) session.getAttribute("current.user.fn") + " "); %> </label> 
				<label for="surname"> <% out.print((String) session.getAttribute("current.user.ln")); %> </label> 
				<input type="submit" value="Odjava" />
			</form>
		</div>
	</c:when>
	<c:otherwise>
		<h1>not logged in</h1>
	</c:otherwise>
</c:choose>

</html>