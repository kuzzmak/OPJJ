<%@ page import="hr.fer.zemris.java.hw15.model.BlogComment"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" session="true"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<style>
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
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

.button:hover {
	opacity: 1;
}
</style>

<head>
<%@ include file="Header.jsp"%>
<title>Komentari</title>
</head>

<body>

	<a
		href="<%out.print("comment?blogId=" + (String) request.getParameter("blogId"));%>"
		class="button">komentiraj</a>

	<c:choose>
		<c:when test="${blogEntryComments.size() != 0}">
			<p>Komentari:</p>
			<table>
				<tr>
					<th>e-mail</th>
					<th>poruka</th>
					<th>objavljeno</th>
				</tr>
	        	<c:forEach items="${blogEntryComments}" var="comment">
					<tr>
						<td>${comment.usersEMail}</td>
						<td>${comment.message}</td>
						<td>${comment.postedOn}</td>
					</tr>
				</c:forEach>
			</table>
        <br />
		</c:when>
		<c:otherwise>
        <p>Nema komentara.</p>
        <br />
		</c:otherwise>
	</c:choose>

</body>
</html>