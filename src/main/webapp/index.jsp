<%@ page import="java.util.List" %>
<%@ page import="com.flyaway.models.Places" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.time.LocalDate" %><%--
  Created by IntelliJ IDEA.
  User: san
  Date: 20-Sep-20
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book New Ticket</title>
</head>
<body style="background-color: lightblue">
<div>
    <form action="/login" method="get">
        <input type="submit" value="Admin Login">
    </form>
</div>

<% List<Places> places =  null;
    if(request.getAttribute("places") != null) {
     places = (List<Places>) request.getAttribute("places");
} else {
    response.sendRedirect("/home");
    return;
} if(places.size()  == 0) { %>
       <h3 style="color: red">There are no flights available right now..</h3>
<% return; } %>


    <h1 style="alignment: center"> Welcome to Flyaway ticket booking portal </h1>
    <form method="post" action="home">
        <label for="travelDate">Choose travel Date Date </label>

        <input type="date" id="travelDate" name="travelDate"
        min="<%=LocalDate.now().plusDays(1)%>" max="<%= LocalDate.now().plusMonths(3)%>"> <br><br>
        <label for="place"> Select your Source and destination</label>
        <select name="place" id="place">
             <% for(Places place : places ) { %>
            <option value="<%=place.getId()%>">
                <%= place.getSource() %> to <%= place.getDestination()%></option>
                <%}%>
            </select> <br> <br>
        <label for="passengers"> select number of Passengers</label>
        <select id="passengers" name="passengers">
        <% for( int i=0;i<10;i++) { %>
            <option value="<%= i%>"><%= i %></option>
        <% } %>
        </select> <br> <br>

        <input type="submit" value="search">
    </form>
</body>
</html>
