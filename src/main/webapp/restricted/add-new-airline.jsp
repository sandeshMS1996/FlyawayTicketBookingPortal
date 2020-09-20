<%--
  Created by IntelliJ IDEA.
  User: san
  Date: 19-Sep-20
  Time: 1:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="background-color: chartreuse">
<%if (request.getAttribute("message") != null) { %>
<h3 style="color: red"><%= request.getAttribute("message")%>
</h3>
<% } %>
<h1> Add new Airline:</h1>
<form action="/restricted/add-new-airline" method="post">
    <label> Airlines Name*:
        <input type="text" name="airlineName" size="30"/> <br> <br></label>
    <label> Airline Home country(*):
        <input type="text" name="airlineCountry" size="30"/> <br> <br> </label>
    <input type="submit" value="Add new Airline">
</form>

</body>
</html>
