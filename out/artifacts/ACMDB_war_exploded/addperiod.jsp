<%@ page import="acmdb.thModel" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="acmdb.periodsModel" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.Objects" %>
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
    String a = null, b = null, thid = null;
    if (session.getAttribute("startdate") != null)
        a = (String) session.getAttribute("startdate");
    else
        a = request.getParameter("startdate");

    if (session.getAttribute("enddate") != null)
        b = (String) session.getAttribute("enddate");
    else
        b = request.getParameter("enddate");

    if (session.getAttribute("thid") != null)
        thid = (String) session.getAttribute("thid");
    else
        thid = request.getParameter("thid");
    int pid = -1;
    if ((a != null) && (b != null)){
        session.setAttribute("startdate", a);
        session.setAttribute("enddate", b);
        session.setAttribute("thid", thid);
%>
        Are you sure to execute this reservation?
        <form action="addperiod.jsp" method="get">
            <div><label>Yes<input type="radio" name="radio" value="yes"></label></div>
            <div><label>No<input type="radio" name="radio" value="no"></label></div>
            <div><input type="submit"></div>
        </form>
<%
        if (Objects.equals(request.getParameter("radio"), "no")){
            session.setAttribute("startdate", null);
            session.setAttribute("enddate", null);
            session.setAttribute("thid", null);
%>
            <strong>Modification cancelled.</strong>
            <br>You can now refresh this page to start a new addition or back to homepage.<br>
<%
        }
        if (Objects.equals(request.getParameter("radio"), "yes")){
            try{
                periodsModel pModel = new periodsModel();
                int tid = Integer.parseInt(thid);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date stime = new java.sql.Date (sdf.parse(a).getTime());
                java.sql.Date etime = new java.sql.Date (sdf.parse(b).getTime());
                pid = pModel.addperiods(tid, stime, etime);
            }catch (Exception e) {
                pid = -1;
            }
            if (pid < 0){
%>
                <strong>Addition failed. Please check your information.</strong>
<%
            }
            else{
%>
                <strong>Addition succeeded!</strong> New period ID is <%=pid%>.<br>
<%
            }
        }
    }
%>
<br>
<a href="index.jsp">back</a>
</body>
</html>
