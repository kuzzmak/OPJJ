<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<style>
form {
	border: 3px solid #f1f1f1;
}

/* Full-width inputs */
input[type=text], input[type=password] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

/* Set a style for all buttons */
button {
	background-color: #4CAF50;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
}

/* Add a hover effect for buttons */
button:hover {
	opacity: 0.8;
}

/* Extra style for the cancel button (red) */
.cancelbtn {
	width: auto;
	padding: 10px 18px;
	background-color: #f44336;
}

/* Add padding to containers */
.container {
	padding: 16px;
}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) {
	span.password {
		display: block;
		float: none;
	}
}
</style>

<head>
<title>Blog</title>
</head>

<form action="login" method="post">

	<div class="container">
		<label for="username"><b>Korisničko ime</b></label> <input type="text"
			placeholder="Upišite korisničko ime" name="username" required>

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

</html>