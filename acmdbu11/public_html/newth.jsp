<%@ page import="java.sql.SQLException" %>
<%@ page import="acmdb.thModel" %><%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/6/2
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uotel - NewTH</title>
</head>
<body>
<h1>Add a new TH</h1><br>
<form action="newth.jsp" method="get">
    <div><label>House name* <input name="housename" type="text"></label></div>
    <div><label>Address <input name="address" type="text"></label></div>
    <div><label>URL <input name="url" type="text"></label></div>
    <div><label>Telephone <input name="tel" type="text"></label></div>
    <div><label>Year built* <input name="years" type="text"></label></div>
    <div><label>Price* <input name="price" type="text"></label></div>
    <div><input type="submit"></div>
</form>
<%
    String housename = request.getParameter("housename");
    String address = request.getParameter("address");
    String url = request.getParameter("url");
    String tel = request.getParameter("tel");
    String year = request.getParameter("years");
    String price = request.getParameter("price");
    if (year == null || price == null) {
        if (housename != null || address != null || url != null || tel != null){
%>
            <strong>Addition failed!</strong> More information is required!<br>
<%
        }
    }
    else{
    thModel model = null;
    model = new thModel();
    int THId = 0;
    try {
        THId = model.addTH(housename, address, url, tel, Integer.parseInt(year), Integer.parseInt(price), (Integer) session.getAttribute("userid"));
    } catch (SQLException e) {
        e.printStackTrace();
%>
        <strong>Addition failed!</strong> Please check your information!<br>
<%
    }
    if  (THId != 0){
%>
        <strong>Addition succeeded! </strong> Your TH id is <%=THId%>.<br>
<%
    }}
%>
<br>
<a href="index.jsp">back</a>
</body>
</html>
