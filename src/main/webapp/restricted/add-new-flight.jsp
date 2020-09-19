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
</head>
<body style="background-color: chartreuse">
<% if (request.getAttribute("airLines") == null) {
    System.out.println("airline = null");
    response.sendRedirect("/restricted/add-new-flight");
} else if (((Map<Integer, String>) request.getAttribute("airLines")).size() == 0) {
    System.out.println("airline size = 0");
%>
<h1> Please register a Airline before adding Flights..</h1>
<% response.sendRedirect("/restricted/add-new-airline");
} else { %>

<h1> Add new Flight...</h1>
<% Map<Integer, String> pairs = (Map<Integer, String>) request.getAttribute("airLines");
%>
<form action="/restricted/add-new-flight" method="post">
    <label> select Airlines:
        <select name="airline">
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
    <select name="frequency" id="frequency" sele>
        <% FlightSchedule[] values = FlightSchedule.values();
            for (FlightSchedule value : values) {
                %>if(value.equals("EVERYDAY")) {
                    <option value="<%= value%>" selected><%= value %></option>
                } else {
                    <option value="<%= value%>"><%= value %></option>
        <% } %>
    </select> <br><br>
    <label>Total Number of Seats
        <input type="number" value="200"/> </label><br>
    <label> price:
        <input type="number">
    </label> <br><br>

    <input type="submit" value="submit">
</form>
<% } %>
</body>
</html>
