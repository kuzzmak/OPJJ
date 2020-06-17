<%@page import="java.util.function.ToLongFunction"%>
<%@page import="hr.fer.zemris.java.hw14.DAOProvider"%>
<%@page import="hr.fer.zemris.java.hw14.PollEntry"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.function.ToLongFunction"%>
<%@page import="java.util.List" contentType="text/html; charset=UTF-8"
	session="true" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- stranica za prikaz rezultata glasanja za najbolji bend --%>

<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>

<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="e" items="${pollResults}">
				<tr>
					<td>${e.getTitle()}</td>
					<td>${e.getVotesCount()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Grafički prikaz rezultata</h2>

	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
	<h2>Rezultati u XLS formatu</h2>

	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pobjednika:</p>
	<ul>
		<%
		    Long pollID = Long.parseLong(String.valueOf(request.getSession().getAttribute("pollID")));
			List<PollEntry> entries = DAOProvider.getDao().getPollEntryList(pollID);
			List<PollEntry> maxVotesEntries = new ArrayList<>();
	
			// maksimalni broj glasova
			final Long maxVotes = entries.stream().mapToLong(new ToLongFunction<PollEntry>() {
	
				@Override
				public long applyAsLong(PollEntry entry) {
					return entry.getVotesCount();
				}
			}).max().getAsLong();
	
			for (PollEntry entry : entries) {
				if (entry.getVotesCount() >= maxVotes) {
					maxVotesEntries.add(entry);
				}
			}
	
			for (PollEntry entry : maxVotesEntries) {
				out.print("<li><a href=\"");
				out.print(entry.getLink());
				out.print("\" target=\"_blank\">");
				out.print(entry.getTitle());
				out.print("</a></li>");
			}
		%>
	</ul>
</body>
</html>