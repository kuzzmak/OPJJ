<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- stranica gdje se glasa za najbolji bend --%>

<html>   
	<body style="background-color:${pickedBgColor};">
		<h1>Glasanje za omiljeni bend:</h1>     
		<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
		
		<ol>
		<c:forEach var="e" items="${bands}">
		<li><a href="glasanje-glasaj?id=${e.key}">${e.value}</a></li>
		</c:forEach>
		</ol>
	</body> 
</html>
