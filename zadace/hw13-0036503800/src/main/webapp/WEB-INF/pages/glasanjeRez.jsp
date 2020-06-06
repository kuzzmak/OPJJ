<%@ page import="java.util.Map" contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- stranica za prikaz rezultata glasanja za najbolji bend --%>

<html>   
	<head>
		<style type="text/css">       
			table.rez td {text-align: center;}     
		</style>   
	</head>
	
	<body style="background-color:${pickedBgColor};">
		<h1>Rezultati glasanja</h1>     
		<p>Ovo su rezultati glasanja.</p>     
		<table border="1" cellspacing="0" class="rez">       
		<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>       
		<tbody>
		
		<c:forEach var="e" items="${results}">
		<c:set var="bandId" value="${e.key}"/>
		<tr><td><c:out value="${bands[bandId]}"/> </td><td> ${e.value} </td></tr>
		</c:forEach>
		
		</tbody>     
		</table>          
		<h2>Grafički prikaz rezultata</h2>     
		
		<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />          
		<h2>Rezultati u XLS formatu</h2>     
		
		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>          
		
		<h2>Razno</h2>     
		<p>Primjeri pjesama pobjedničkih bendova:</p>     
		<ul>
			<%
				Map<Long, String> bandLinks = (Map<Long, String>) request.getServletContext().getAttribute("bandLinks");
				Map<Long, Integer> results = (Map<Long, Integer>) request.getServletContext().getAttribute("results");
				Map<Long, String> bands = (Map<Long, String>) request.getServletContext().getAttribute("bands");
				
				int votes = 0;
	
				for(Map.Entry<Long, Integer> entry: results.entrySet()){
					if(entry.getValue() >= votes){
						votes = entry.getValue();
						out.print("<li><a href=\"");
						out.print(bandLinks.get(entry.getKey()));
						out.print("\" target=\"_blank\">");
						out.print(bands.get(entry.getKey()));
						out.print("</a></li>");
					}
				}
			%>
		</ul>
	</body>
</html>