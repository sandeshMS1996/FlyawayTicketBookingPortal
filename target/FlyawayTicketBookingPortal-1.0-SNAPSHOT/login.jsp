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
    <style>
        .button {
            font: bold 11px Arial;
            text-decoration: none;
            background-color: #EEEEEE;
            color: #333333;
            padding: 2px 6px 2px 6px;
            border-top: 1px solid #CCCCCC;
            border-right: 1px solid #333333;
            border-bottom: 1px solid #333333;
            border-left: 1px solid #CCCCCC;
        }
    </style>

</head>
<body style="background-color: chartreuse">

<div>
    <h1> Login to Flyaway Air Ticket booking Admin portal </h1>
    <h3> Not Registered? <a class="button" href="/register">Register</a> </h3>
</div>

<% if ( request.getAttribute("message")  != null) { %>

<h3 style="color: red">  <%=  request.getAttribute("message") %> </h3>
  <%  } %>
<% int count  = 0;
    if(session != null && session.getAttribute("loginAttempts") != null )
        count = (Integer) session.getAttribute("loginAttempts");
 if (count != 0 && count < 4)  { %>
<p style="color: red">Login Attempt:  <%= count %> </p>
  <%  }
if ( count >= 4) { %>
<p style="color: red"> Login access revoked, please try again after some times</p>
<% }
else  { %>
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
<% } %>
    <a href="/change-password">Forgot Password</a>
</body>
</html>
