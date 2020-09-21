<%@ page import="com.flyaway.models.Flights" %><%--
  Created by IntelliJ IDEA.
  User: san
  Date: 21-Sep-20
  Time: 9:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
        <title>Book your ticket</title>
        <style>
            table{
                border: 1px solid black;
                width:70%;
                margin-left:15%;
                margin-right:15%;

            }
            tr,td {
                border: 1px solid black
            }

        </style>
</head>
<body style="background-color: lightblue">
<% HttpSession session1 = request.getSession(false);
    if(session1 ==  null || session1.getAttribute("isValidRequest")  == null ) {
        response.sendRedirect("/home");
        return;
}%>
<% session1.invalidate();%>
<h1> Make your Payment: </h1>
<h3> Please do not refresh this page...</h3>
<table>
    <tr>
        <td colspan="2" style="text-align: center; background-color: cornflowerblue"><h3> Trip Details</h3></td>
    </tr>
    <tr>
        <td style="background-color: #CCCCCC">Airline Company</td>
        <td> <%= request.getParameter("name")%></td>
    </tr>
    <tr>
        <td style="background-color: #CCCCCC">From</td>
        <td> <%= request.getParameter("from")%></td>
    </tr>
    <tr>
        <td style="background-color: #CCCCCC">To</td>
        <td><%= request.getParameter("to") %></td>
    </tr>
    <tr>
        <td style="background-color: #CCCCCC">Price</td>
        <td><%= request.getParameter("price") %></td>
    </tr>
    <tr>
        <td style="background-color: #CCCCCC">Departure time </td>
        <td><%= request.getParameter("time") %> Hrs</td>
    </tr>



</table>
<br>

<h1> Provide your Dummy payment details:</h1> <br>

    <form method="post" action=".">
        Email ID(YOur ticket would be sent to this email ID):
        <input type="email" name="CustomerEmail"> <br><br>
        Phone Number: <input type="tel" name = "phone"> <br> <br>
        Amount: <input type="text" value="200" disabled maxlength="10"> <br> <br>
        UPI ID: <input type="text" placeholder="UPI ID"> <br> <br>
        <input type="submit" value="Make Payment">
    </form>

        </body>
</html>
