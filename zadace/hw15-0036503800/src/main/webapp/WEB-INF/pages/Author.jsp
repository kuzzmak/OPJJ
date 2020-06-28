<%@ page import="hr.fer.zemris.java.hw15.model.BlogEntry" %> 
<%@ page import="hr.fer.zemris.java.hw15.dao.DAOProvider" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<%@ include file="Header.jsp" %>  
</head>

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

a {
  padding: 0px 10px;
  word-wrap: normal;
  display: inline-block;
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
</style>

<body>

	<%
		String nick = (String) session.getAttribute("current.user.nick");
	    if(nick != null){
			if(nick.equals(request.getParameter("nick"))){
				out.print("<a href=\"newBlogEntry\" class=\"button\">Nova objava</a>");
			}
	    }
	%>

	<p> Lista objava autora <%= request.getParameter("nick") %>:</p>
	<table>
		<tr>
			<th>Naslov</th>
			<th>Tekst</th>
			<th>Objavljeno</th>
			<th></th>
		</tr>
		
	 <%
	 	String user = request.getParameter("nick");
	 	if(user != null){
	 		
	 		List<BlogEntry> userEntries = DAOProvider.getDAO().getUserEntries(user);
	 		
	 		for(BlogEntry be: userEntries){
	 			
	 			out.print("<tr>");
				
	 			out.print("<th>" + be.getTitle() + "</th>");
	 			out.print("<th>" + be.getText() + "</th>");
	 			out.print("<th>" + be.getCreatedAt().toString() + "</th>");
	 			if(nick != null && nick.equals(user)){
			 		out.print("<th><a href=\"comments?blogId=" + be.getId() + "\"" + "\">" + "komentari" + "</a> <a href=\"editBlogEntry?blogId=" + be.getId() + "\"" + ">uredi</a>" + "</th>");
	 			}else{
	 				out.print("<th><a href=\"comments?blogId=" + be.getId() + "\"" + "\">" + "komentari" + "</a>" + "</th>");
	 			}
	 			out.print("<tr>");
	 		}
	 	}
	 %>
		
	</table>
	
</body>

</html>