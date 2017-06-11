<%@ page import="acmdb.favorModel" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Objects" %>
<%@ page import="acmdb.thModel" %><%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/6/2
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uotel - Choose Your Favourite</title>
</head>
<body>
<h1>Mark your favourite TH</h1><br>
We now have the following THs:<br>

<%
    thModel thmodel = new thModel();
    ResultSet cand = thmodel.getAll();
    while   (cand.next()){
%>
ID <%=cand.getInt("id")%>: <%=cand.getString("housename")%>, at price <%=cand.getInt("price")%>.<br>
<%
    }
%>

<br>You can now choose one as your favourite TH:<br>
<form action="choosefav.jsp" method="get">
    <div><label>TH ID <input name="id" type="text"></label></div>
    <div><input type="submit"></div>
</form>

<%
    String id = request.getParameter("id");
    if (id != null && !Objects.equals(id, "")) {
        favorModel model = new favorModel();
        int valid = 1;
        try{
            model.addfavors((Integer) session.getAttribute("userid"), Integer.parseInt(id));
        }
        catch (SQLException e){
            e.printStackTrace();
            valid = 0;
%>
<strong>ID must be a number!</strong>
<%
        }
        if  (valid == 1){
%>
<strong>Update succeeded!</strong><br>
You have the following favourite THs now:
<%
            ResultSet res = model.query((Integer) session.getAttribute("userid"));
            while (res.next()){
%>
 <%=res.getInt("thid")%>
<%
            }
%>
<br><br>
<%
        }
    }
%>
<br>
<a href="index.jsp">back</a>
</body>
</html>
