<%@ page import="java.sql.ResultSet" %>
<%@ page import="acmdb.resModel" %><%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/6/2
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uotel - Record a Stay</title>
</head>
<body>
<h1>Record a stay</h1><br>
You have the following reservations that haven't been recorded with a stay:<br>

<%
    resModel rmodel = new resModel();
    ResultSet res = rmodel.getres((Integer) session.getAttribute("userid"));
    while   (res.next()){
%>
Reservation with ID=<%=res.getInt("id")%> for TH ID=<%=res.getInt("thid")%>.<br>
<%
    }
%>

<br>You can now choose a reservation and record a stay.<br>
<form action="recstay.jsp" method="get">
    <div><label>Reservation ID: <input type="text" name="resid"></label></div>
    <div><input type="submit"></div>
</form>
<%
    String resid = request.getParameter("resid");
    if (resid != null){
        int rec = rmodel.visit(Integer.parseInt(resid));
        if (rec > 0){
%>
            <strong>Stay has been successfully recorded.</strong> You can now refresh the page to see the update.<br>
<%
        }
    }
%>
<br>
<a href="index.jsp">back</a>
</body>
</html>
