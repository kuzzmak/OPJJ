<%@ page import="hr.fer.zemris.java.hw15.model.BlogComment"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<style>

/* Set a style for the submit/register button */
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

	<a href="<% out.print("comment?blogId=" + (String) request.getParameter("blogId")); %>" class="button">komentiraj</a>

	<c:forEach items="${blogEntryComments}" var="comment">
		<tr>
			<td>${comment.usersEMail}</td>
			<td>${comment.message}</td>
			<td>${comment.postedOn}</td>
		</tr>
	</c:forEach>

</body>
</html>