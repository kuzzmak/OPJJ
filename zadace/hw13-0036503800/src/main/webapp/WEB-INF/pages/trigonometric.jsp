<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- stranica za prikaz vrijednosti funkcija sinus i kosinus --%>

<html>
	<style>
	th, td {
		border: 1px solid black;
		padding:0 5px 0 5px;
	}
	</style>
	<body style="background-color:${pickedBgColor};">
		 <table style="text-align:center; margin-left:auto; margin-right:auto;">
			<tr> <td> broj </td> <td> sin broja </td> <td> cos broja </td>
			<c:forEach var="e" items="${entries}">
			<tr> 
				<td> ${e.getNumber()} </td> 
				<td> ${e.getSinValue()} </td> 
				<td> ${e.getCosValue()} </td>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>