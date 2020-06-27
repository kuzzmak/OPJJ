<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

h1 {text-align: center;}
p {text-align: center;}
a {text-align: center;}

.button:hover {
	opacity: 1;
}
</style>


	<head>
		<title>Pogreška</title>
	</head>

	<body>
		<h1>Dogodila se pogreška</h1>
		<p><c:out value="${error}"/></p>

		<p><a href="main" class="button">Povratak na početnu stranicu</a></p>
	</body>
</html>