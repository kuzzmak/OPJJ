<%@ page contentType="text/html; charset=UTF-8" session="true"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<%
	Object currentNick = session.getAttribute("current.user.nick");

	if (currentNick != null) {
		request.setAttribute("nick", currentNick);
	} 
%>

<c:choose>
	<c:when test="${nick != null}">
	
		<h1><% out.print((String) session.getAttribute("current.user.fn")); %></h1>
		<h1><% out.print((String) session.getAttribute("current.user.ln")); %></h1>
	
		<form action="logout">
			<input type="submit" value="Odjava" />
		</form>
		<br />
	</c:when>
	<c:otherwise>
        <h1>anonymous</h1>
        <br />
	</c:otherwise>
</c:choose>

</html>