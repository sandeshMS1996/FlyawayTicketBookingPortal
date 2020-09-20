<%@ page import="com.flyaway.models.FlightSchedule" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: san
  Date: 19-Sep-20
  Time: 3:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new Flight</title>
    <style>
        body {
            position: center;

        }
        .select-box {
            display: block;
            margin: 0 auto;
            font-family: 'Open Sans', 'Helvetica Neue', 'Segoe UI', 'Calibri', 'Arial', sans-serif;
            font-size: 18px;
            color: #60666d
        }
    </style>
</head>
<body style="background-color: chartreuse">
<% if (request.getAttribute("airLines") == null) {
    System.out.println("airline = null, redirecting to get method");
    response.sendRedirect("/restricted/add-new-flight");
    return;
} else if (((Map<Integer, String>) request.getAttribute("airLines")).size() == 0) {
    System.out.println("airline size = 0");
%>
<h1> Please register a Airline before adding Flights..</h1>
<% response.sendRedirect("/restricted/add-new-airline");
    return;
} else { %>

<h1> Add new Flight...</h1>
<% Map<Integer, String> pairs = (Map<Integer, String>) request.getAttribute("airLines");
%>
<form action="/restricted/add-new-flight" method="post">
    <label> select Airlines:
        <select name="airline" class="select-box">
            <% for (Map.Entry<Integer, String> i : pairs.entrySet()) { %>
            <option value="<%= i.getKey() %>"><%= i.getValue()%>
            </option>
            <% } %>
        </select>
    </label>

    <label> From:
        <input type="text" name="from" size="30"/> <br> <br></label>
    <label> To(*):
        <input type="text" name="to" size="30"/> <br> <br> </label>
    <label> Departure*:
        <input type="time" name="departure" size="30"/> <br> <br></label>
    <label for="frequency"> Frequency</label>
    <select name="frequency" id="frequency" multiple class="select-box">
        <% FlightSchedule[] values = FlightSchedule.values();
            for (FlightSchedule value : values) { %>
        <option value="<%= value%>"><%= value %>
        </option>
        <% } %>
    </select> <br><br>
    <label>Total Number of Seats
        <input type="number" value="200"/> </label><br>
    <label> price:
        <input type="number" , name="price">
    </label> <br><br>
    <input type="submit" value="submit">
</form>
<% } %>
</body>
</html>
