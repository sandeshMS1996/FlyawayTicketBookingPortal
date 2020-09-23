<%@ page import="com.flyaway.models.Flights" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
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
    if(session1 ==  null || session1.getAttribute("isValidRequest")  == null ||
            session1.getAttribute("travelDate") == null) {
        response.sendRedirect("/home");
        System.out.println("Returnng to home.. from book ticket");

        return;
}%>
<% session1.setAttribute("isValidRequest", null);
    session1.setAttribute("flightId", request.getParameter("id"));
    session1.setAttribute("price", request.getParameter("price") );
    session1.setAttribute("isValidPaymentRequest" , true);
%>

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
        <td><%request.getParameter("price");%> </td>
    </tr>
    <tr>
        <td style="background-color: #CCCCCC">Travel  Date </td>
        <td><%= ((LocalDate) session1.getAttribute("travelDate")).format(
                DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        )%></td>
    </tr>

    <tr>
        <td style="background-color: #CCCCCC">Departure time </td>
        <td><%= request.getParameter("time") %> Hrs</td>
    </tr>

</table>
<br>

<h1> Provide your Dummy payment details:</h1> <br>
    <% System.out.println("passing control to Payment gateway servlet"); %>
    <form method="post" action="payment-gateway">
        Name: <input type="text" name="firstname"> <br> <br>
        Email ID(YOur ticket would be sent to this email ID):
        <input type="email" name="email"> <br><br>
        Phone Number: <input type="text" name = "phone"> <br> <br>
        UPI ID: <input type="text" placeholder="example.me@upi.com" name="upiId"> <br> <br>
        Number of Passengers: <input type="number" min="1" max="10" id = "numberOfPassengers" name="numberOfPassengers">
        <input type="submit" value="Make Payment">
    </form>

        </body>
</html>
