<%--
  Created by IntelliJ IDEA.
  User: san
  Date: 16-Sep-20
  Time: 1:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Login</title>
</head>
<body style="background-color: chartreuse">
<h1> Login to Flyaway Air Ticket booking Admin portal </h1>

<% if ( session.getAttribute("loginAttempts")  != null) { %>
    <p style="color: red">Login Attempt: <%  session.getAttribute("loginAttempts");
    } %>

<form action="/login" method="post">
    <table style="with: 50%">

        <tr>
            <td>Email ID: </td>
            <td><input type="text" name="email" /></td>
        </tr>
        <tr>
            <td>Password: </td>
            <td><input type="password" name="password" /></td>
        </tr>
    </table>
    <input type="submit" value="Login" /></form>
    <a href="">Forgot Password</a>
</body>
</html>
