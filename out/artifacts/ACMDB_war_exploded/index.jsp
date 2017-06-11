<%@ page import="acmdb.userModel" %><%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/5/30
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Uotel - Homepage</title>
    </head>
    <body>
    <h1>Uotel: An Airbnb-like housing system</h1><br>
<%
    session.setAttribute("houseid", null);
    session.setAttribute("periodid", null);
    String thisusername = (String) session.getAttribute("username");
    if  (thisusername == null){
%>
        If you already have an account:
        <form action="login.jsp" method="post">
            <div><label>Username</label> <input name="username" type="text"></div>
            <div><label>Password</label> <input name="password" type="password"></div>
            <div></div><input type="submit" value="login"><div>
        </form>
        You can also <a href="register.jsp"> register</a> an account.
<%
    }
    else{
%>
    <h2>Hi <%=thisusername%> ,what would you like to do?</h2><br>
    1. <a href = "newth.jsp">Add a new TH</a><br>
    2. <a href = "updateth.jsp">Update information of your TH</a><br>
    3. <a href = "addperiod.jsp">Add a period</a><br>
    4. <a href = "addres.jsp">Add a new reservation</a><br>
    5. <a href = "recstay.jsp">Record a stay</a><br>
    6. <a href = "choosefav.jsp">Mark your favourite TH</a><br>
    7. <a href = "degree.jsp">Find "degree of separation" between two users</a><br><br>
    <a href = "logout.jsp">logout</a><br>
<%
    }
%>
    </body>
</html>
