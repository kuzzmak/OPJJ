<%@ page contentType="text/html; charset=UTF-8" session="true"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<style>

input[type=text], select, textarea {
  width: 100%; /* Full width */
  padding: 12px; /* Some padding */ 
  border: 1px solid #ccc; /* Gray border */
  border-radius: 4px; /* Rounded borders */
  box-sizing: border-box; /* Make sure that padding and width stays in place */
  margin-top: 6px; /* Add a top margin */
  margin-bottom: 16px; /* Bottom margin */
  resize: vertical /* Allow the user to vertically resize the textarea (not horizontally) */
}

input[type=submit] {
  background-color: #4CAF50;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}

.container {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}

</style>

<head>
<%@ include file="Header.jsp"%>
<title>Komentar</title>
</head>

<body>

<%-- <a href="<% out.print("comment?blogId=" + (String) request.getParameter("blogId")); %>" class="button">komentiraj</a> --%>

<div class="container">

	  <form action="comment" method="post">
	
	    <label for="message">Poruka</label>
	    <textarea id="message" name="message" placeholder="Upišite tekst poruke..." style="height:200px"></textarea>
	
	    <input type="submit" value="Komentiraj">
	
	  </form>
	</div>

</body>

</html>