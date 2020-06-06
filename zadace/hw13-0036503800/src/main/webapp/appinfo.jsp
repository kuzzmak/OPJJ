<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- informacije o vremenu aktivnosti servera --%>

<html>
	<%
	
	Long timeStarted = Long.parseLong(String.valueOf(getServletContext().getAttribute("timeStarted")));
	Long currentTime = System.currentTimeMillis();
	
	Long elapsed = currentTime - timeStarted;
	Long secondsElapsed = elapsed / 1000;
	Long minutesElapsed = elapsed / (60 * 1000);
	Long hoursElapsed = elapsed / (60 * 60 * 1000);
	Long daysElapsed = elapsed / (24 * 60 * 60 * 1000);
	%>
	
	<body style="background-color:${pickedBgColor};">
		<h1>Vrijeme proteklo od pokretanja servera:</h1>
		<p> Dana: <%= daysElapsed %></p>
		<p> Sati: <%= hoursElapsed %></p>
		<p> Minuta: <%= minutesElapsed %></p>
		<p> Sekundi: <%= secondsElapsed %></p>
		<p> Miliekundi: <%= elapsed %></p>
	</body>
</html>