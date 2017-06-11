<%@ page import="acmdb.userModel" %><%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/6/2
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uotel - Login</title>
</head>
<body>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    if (username != null && password != null) {
        userModel model = new userModel();
        int userId = model.checkLogin(username, password);
        if (userId != -1) {
            session.setAttribute("userid", userId);
            session.setAttribute("username", username);
%>
            <div><strong>Welcome back, </strong> <%=username%>.</div>
<%
        }
        else{
%>
            <div><strong>Invalid username or password.</strong></div>
<%
        }
    }
%>
<a href="index.jsp">back</a>
</body>
</html>
