<%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/5/31
  Time: 3:18
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="acmdb.userModel" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <title>Uotel - Register</title>
</head>
<body>
<h1>Register your own account</h1>

<form action="register.jsp" method="get">
    <div><label>Username* <input name="username" type="text"></label></div>
    <div><label>Password* <input name="password" type="password"></label></div>
    <div><label>Fullname <input name="fullname" type="text"></label></div>
    <div><label>Address <input name="address" type="text"></label></div>
    <div><label>Phone number <input name="phone" type="text"></label></div>
    <div><input type="submit"></div>
</form>

<%
    String fullname = request.getParameter("fullname");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String address = request.getParameter("address");
    String phone = request.getParameter("phone");
    if (username != null && password != null){
        userModel model = null;
        model = new userModel();
        int userId = 0;
        try {
            userId = model.registerUser(username, password, fullname, address, phone);
        } catch (SQLException e) {
%>
            <strong>Registration failed!</strong> Please check your information or try another username.
<%
        }
        if  (userId != 0){
            session.setAttribute("userid", userId);
            session.setAttribute("username", username);
%>
            <strong>Registration succeed! </strong> Your user id is <%=userId%>.
<%
        }
    }
%>
<br>
<a href="index.jsp">Back</a>
</body>
</html>
