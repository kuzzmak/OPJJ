<%@ page contentType="text/html; charset=UTF-8" session="true"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="Header.jsp" %>  
</head>

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
<title>Nova objava</title>
</head>


<body>

	<div class="container">
	  <form action="newBlogEntry" method="post">
	
	    <label for="blogTitle">Title</label>
	    <input type="text" id="blogTitle" name="blogTitle" placeholder="Naslov objave...">
	
	    <label for="tet">Tekst objave</label>
	    <textarea id="text" name="text" placeholder="Upišite tekst objave..." style="height:200px"></textarea>
	
	    <input type="submit" value="Spremi">
	
	  </form>
	</div>

</body>

</html>