<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- početna stranica --%>

<html>
	<style>
	form {
		width: 100px;
		margin: 0 auto;
	}
	</style>
	<body style="background-color:${pickedBgColor};">
		<a href="setcolor"><p style="text-align:center">Background color chooser</p></a>
		<a href="stories/funny.jsp"><p style="text-align:center">Funny stories</p></a>
		<a href="report.jsp"><p style="text-align:center">OS usage</p></a>
		<a href="powers?a=1&b=100&n=3"><p style="text-align:center">Powers</p></a>
		<a href="appinfo.jsp"><p style="text-align:center">App info</p></a>
		<a href="glasanje"><p style="text-align:center">Glasanje</p></a>
		
		<form action="trigonometric" method="GET">  
			Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>  
			Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>  
			<input type="submit" value="Tabeliraj"><input type="reset" value="Reset"> 
		</form> 
	</body>
</html>