<%@page import="hr.fer.zemris.java.hw15.model.BlogUser"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.hw15.dao.DAOProvider"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
<%@ include file="Header.jsp"%>
</head>

<style>
form {
	border: 3px solid #f1f1f1;
}

input[type=text], input[type=password] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

button {
	background-color: #4CAF50;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
}

button:hover {
	opacity: 0.8;
}

.cancelbtn {
	width: auto;
	padding: 10px 18px;
	background-color: #f44336;
}

.container {
	padding: 16px;
}

.center {
	text-align: center;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #f2f2f2
}

th {
	background-color: #4CAF50;
	color: white;
}

p {
	text-align: center;
}
</style>

<head>
<title>Blog</title>
</head>

<c:if test="${loginError != null}">
	<p style="color: red">${loginError}</p>
	<br />
</c:if>

<form action="login" method="post">

	<div class="container">
	
		<c:choose>
   			<c:when test="${loginError != null}">
        		<label for="username"><b>Korisničko ime</b></label> <input type="text" value="${triedNick}" name="username" required>
    		</c:when>    
    	<c:otherwise>
        	<label for="username"><b>Korisničko ime</b></label> <input type="text" placeholder="Upišite korisničko ime" name="username" required>
    	</c:otherwise>
		</c:choose>

		<label for="password"><b>Lozinka</b></label> <input type="password"
			placeholder="Upišite lozinku" name="password" required>

		<button type="submit">Ulogiraj se</button>

	</div>

</form>

<form action="register">
	<div class="container">
		<button type="submit">Registracija</button>
	</div>
</form>

<%
	List<BlogUser> users = DAOProvider.getDAO().getAllUsers();

out.print("<table>");

out.print("<tr>");
out.print("<th> korisničko ime </th>");
out.print("<th> ime </th>");
out.print("<th> prezime </th>");
out.print("<th> e-mail </th>");
out.print("</tr>");

for (BlogUser bu : users) {
	out.print("<tr>");
	String link = "<a href=" + "\"author?nick=" + bu.getNick() + "\">" + bu.getNick() + "</a>";
	out.print("<th>" + link + "</th>");
	out.print("<th>" + bu.getFirstName() + "</th>");
	out.print("<th>" + bu.getLastName() + "</th>");
	out.print("<th>" + bu.geteMail() + "</th>");
	out.print("<tr>");
}
out.print("</table>");
%>

</html>