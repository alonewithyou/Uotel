<%@ page import="acmdb.thModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/6/2
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uotel - UpdateTH</title>
</head>
<body>
<h1>Update the information of your TH</h1><br>

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

<br>You can now select a TH and modify its information.<br>

<form action="updateth.jsp" method="get">
    <div><label>TH ID* <input name="id" type="text"></label></div>
    <div><label>House name* <input name="housename" type="text"></label></div>
    <div><label>Address <input name="address" type="text"></label></div>
    <div><label>URL <input name="url" type="text"></label></div>
    <div><label>Telephone <input name="tel" type="text"></label></div>
    <div><label>Year built* <input name="years" type="text"></label></div>
    <div><label>Price* <input name="price" type="text"></label></div>
    <div><input type="submit"></div>
</form>
<%
    String id = request.getParameter("id");
    String housename = request.getParameter("housename");
    String address = request.getParameter("address");
    String url = request.getParameter("url");
    String tel = request.getParameter("tel");
    String year = request.getParameter("years");
    String price = request.getParameter("price");
    if (year == null || price == null) {
        if (housename != null || address != null || url != null || tel != null || id != null){
%>
<strong>Modification failed!</strong> More information is required!<br>
<%
    }
}
    else{
    thModel model = null;
    model = new thModel();
    int THId = 0, running = 1;
    try {
        try {
            THId = model.update((Integer) session.getAttribute("userid"), Integer.parseInt(id), housename, address, url, tel, Integer.parseInt(year), Integer.parseInt(price));
        }
        catch (NumberFormatException n){
            running = 0;
%>
<strong>Invalid yearbuilt or price.</strong>
<%
        }
    } catch (SQLException e) {
        if  (running == 1){
            running = 0;
            e.printStackTrace();
%>
<strong>Error.</strong>
<%
        }
    }
    if  (THId != 0){
%>
<strong>Modification succeeded!</strong> TH id=<%=THId%> has been updated.<br>
You can now refresh the page to see the change.<br>
<%
        }
        else {
            if  (running == 1){
%>
<strong>Modification failed!</strong> No such TH found / You are not the owner of this TH!<br>
<%
            }
        }
    }
%>
<br>
<a href="index.jsp">back</a>
</body>
</html>
