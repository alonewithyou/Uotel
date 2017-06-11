<%@ page import="acmdb.userModel" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/6/2
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uotel - If You Are the One</title>
</head>
<body>
<h1>Find the degree of separation between a pair</h1><br>
You can now choose one for you or some other pair.<br>
<form action="degree.jsp" method="get">
    <div><label>User 1: <input type="text" value=<%=session.getAttribute("username")%> name="user1"></label></div>
    <div><label>User 2: <input type="text" name="user2"></label></div>
    <div><input type="submit"></div>
</form>
</form>
<%
    String user1 = request.getParameter("user1");
    String user2 = request.getParameter("user2");
    int deg = -5;
    if (user1 != null && user2 != null && !Objects.equals(user1, " ") && !Objects.equals(user2, " ")){
        userModel umodel = new userModel();
        try{
            int uid1 = umodel.getUserid(user1);
            int uid2 = umodel.getUserid(user2);
            deg = umodel.degrees(uid1, uid2);
        }catch (Exception e){
%>
            <br><strong>Invalid username.</strong><br>
<%
        }
        if (deg != -5){
%>
            <br>The degree of separation between these two are <%=deg%>.<br>
<%
        }
    }
%>
<br>
<a href="index.jsp">back</a>
</body>
</html>
