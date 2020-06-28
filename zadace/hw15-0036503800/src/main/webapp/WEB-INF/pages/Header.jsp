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

a.button {
	-webkit-appearance: button;
	-moz-appearance: button;
	text-decoration: none;
	appearance: button;
	background-color: #4CAF50;
	color: white;
	padding: 16px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	opacity: 0.9;
}
</style>

<%
	Object currentNick = session.getAttribute("current.user.nick");
	if (currentNick != null) {
		request.setAttribute("nick", currentNick);
	}
%>

<a href="main" class="button">Početna stranica</a>

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


	<%
	    if(currentNick != null){
			out.print("<h1>" + currentNick + "</h1>");
	    }
	%>

</html>