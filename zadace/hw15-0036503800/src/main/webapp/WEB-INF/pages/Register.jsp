<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<style>
* {
	box-sizing: border-box
}

/* Add padding to containers */
.container {
	padding: 16px;
}

/* Full-width input fields */
input[type=text], input[type=password] {
	width: 100%;
	padding: 15px;
	margin: 5px 0 22px 0;
	display: inline-block;
	border: none;
	background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus {
	background-color: #ddd;
	outline: none;
}

/* Overwrite default styles of hr */
hr {
	border: 1px solid #f1f1f1;
	margin-bottom: 25px;
}

/* Set a style for the submit/register button */
.registerbtn {
	background-color: #4CAF50;
	color: white;
	padding: 16px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
	opacity: 0.9;
}

.registerbtn:hover {
	opacity: 1;
}

.center {
 	text-align: center;
}

/* Add a blue text color to links */
a {
	color: dodgerblue;
}

/* Set a grey background color and center the text of the "sign in" section */
.saveUser {
	background-color: #f1f1f1;
	text-align: center;
}
</style>

<head>
<title>Registracija</title>
</head>

<c:if test="${error != null}">
	<p align="center" style="color: red">${error}</p>
</c:if>

<form action="register" method="post">

	<div class="container">

		<h1>Registracija</h1>
		<p>Popunite podatke za stvaranje novog računa.</p>
		<hr>
		
		<label for="name"><b>Ime</b></label> 
			<input type="text" placeholder="Unesite ime" name="name" id="name" required> 
		
		<label for="surname"><b>Prezime</b></label> 
			<input type="text" placeholder="Unesite prezime" name="surname" id="surname" required> 

		<label for="email"><b>E-mail</b></label> 
			<input type="text" placeholder="Unesite e-mail" name="email" id="email" required> 
		
		<label for="username"><b>Korisničko ime</b></label> 
			<input type="text" placeholder="Unesite korisničko ime" name="username" id="username" required> 
		
		<label for="password"><b>Lozinka</b></label> 
			<input type="password" placeholder="Unesite lozinku" name="password" id="password" required> 
		
		<label for="password_repeat"><b>Ponovljena lozinka</b></label> 
			<input type="password" placeholder="Ponovite lozinku" name="password_repeat" id="password_repeat" required>
		<hr>

		<button type="submit" class="registerbtn">Registracija</button>
	</div>

	<div class="center">
		<p>
			Već imate račun? <a href="main">Prijava</a>
		</p>
	</div>

</form>
</html>