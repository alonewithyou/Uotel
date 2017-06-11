<%@ page import="acmdb.thModel" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="acmdb.periodsModel" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/6/3
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Uotel - Add a Period</title>
</head>
<body>
<h1>Add an available period for your TH</h1><br>

You now own the following THs:<br>

<%
    thModel qymodel = new thModel();
    ResultSet res = null;
    try {
        res = qymodel.getTHList((Integer) session.getAttribute("userid"));
    } catch (SQLException e) {
%>
Querying failed!
<%
    }
    while   (res.next()){
%>
TH id <%=res.getInt("id")%> called <%=res.getString("housename")%> at price <%=res.getInt("price")%>.<br>
<%
    }
%>

<br>You can now select a TH and modify its information.<br><br>

<form action="addperiod.jsp" method="get">
    <div><label>TH ID <input name="thid" type="text"></label></div>
    <div><label>Start date <input name="startdate" type="text" value="YYYY-MM-DD"></label></div>
    <div><label>End date <input name="enddate" type="text" value="YYYY-MM-DD"></label></div>
    <div><input type="submit"></div>
</form>

<%
    String a = request.getParameter("startdate");
    String b = request.getParameter("enddate");
    if ((a != null) && (b != null)){
        periodsModel pModel = new periodsModel();
        int thid = Integer.parseInt(request.getParameter("thid"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date stime = new java.sql.Date (sdf.parse(a).getTime());
        java.sql.Date etime = new java.sql.Date (sdf.parse(b).getTime());
        int pid = pModel.addperiods(thid, stime, etime);
%>
<strong>Addition succeeded!</strong> New period ID is <%=pid%>.<br>
<%
    }
%>
<br>
<a href="index.jsp">back</a>
</body>
</html>
