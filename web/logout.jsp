<%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/6/2
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uotel - Logout</title>
</head>
<body>
<%
    session.setAttribute("userid", 0);
    session.setAttribute("username", null);
%>
<h1>You have successfully logout!</h1>
<a href="index.jsp">back</a>
</body>
</html>
