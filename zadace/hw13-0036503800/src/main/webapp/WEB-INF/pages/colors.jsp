<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- stranica za odabir boje koja se koristi kao pozadinska svake stranice --%>

<html>
	<body style="background-color:${pickedBgColor};">
		<a href="/webapp1/setcolor?selectedCol=white"><p style="text-align:center">WHITE</p></a>
		<a href="/webapp1/setcolor?selectedCol=red"><p style="text-align:center">RED</p></a>
		<a href="/webapp1/setcolor?selectedCol=green"><p style="text-align:center">GREEN</p></a>
		<a href="/webapp1/setcolor?selectedCol=cyan"><p style="text-align:center">CYAN</p></a>
	</body>
</html>