<%@ page import="hr.fer.zemris.java.hw14.Poll, hr.fer.zemris.java.hw14.PollEntry" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- stranica gdje se glasa za najbolji bend --%>

<html>   
	<body>
		<%
			Poll poll = (Poll) request.getAttribute("poll");
			out.print("<h1>" + poll.getTitle() + "</h1>");
			out.print("<p>" + poll.getMessage() + "</p>");
		%>
		<ol>
		<c:forEach var="e" items="${entries}">
		<li><a href="glasanje-glasaj?entryID=${e.getId()}">${e.getTitle()}</a></li>
		</c:forEach>
		</ol>
	</body> 
</html>
