<%@ page import="com.flyaway.models.Flights" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: san
  Date: 21-Sep-20
  Time: 8:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choose Your Flight</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

    </style>
</head>
<body style="background-color: lightblue">
<% List<Flights> flights = null;
    if (request.getAttribute("flights") != null)
        flights = (List<Flights>) request.getAttribute("flights");
    else {
        response.sendRedirect("/home");
        return;
    } %>
<% if (flights.size() == 0) { %>
<h3 style="color: crimson"> No Flights available for your criteria </h3>
<% return;
} %>

<% HttpSession session1 = request.getSession(false);
    if (session1 == null || session1.getAttribute("numberOfPassengers") == null
            || session1.getAttribute("travelDate") == null) {
        System.out.println("Returning to home.. from choose your flight");

        response.sendRedirect("/home");
        return;
    }
    session1.setAttribute("isValidRequest", true); %>
<h1> Please select from the below available Flights</h1>
<table>
    <tr>
        <th>Airline</th>
        <th> From</th>
        <th> To</th>
        <th> price</th>
        <th> Departure Time</th>
    </tr>
    <%
        for (Flights flight : flights) { %>

    <tr>
        <td><a href="<%= buildQueryString(flight)%>">
            <%= flight.getParentAirline().getName()%>
        </a></td>
        <td><%= flight.getPlaces().getSource()%>
        </td>
        <td><%= flight.getPlaces().getDestination()%>
        </td>
        <td><%= flight.getPrice()%>
        </td>
        <td><%= flight.getDepartureTime()%>
        </td>
    </tr>

    <% } %>

    <%! public String buildQueryString(Flights flights) throws UnsupportedEncodingException {
        return String.format("BookTicket.jsp?id=%s&name=%s&from=%s&to=%s&price=%s&time=%s",
                flights.getId(),
                flights.getParentAirline().getName(),
                flights.getPlaces().getSource(),
                flights.getPlaces().getDestination(),
                flights.getPrice(),
                flights.getDepartureTime()).replaceAll(" ", "%20%");
    } %>
</table>

</body>
</html>
