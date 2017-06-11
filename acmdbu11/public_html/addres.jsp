<%@ page import="acmdb.resModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="acmdb.thModel" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="acmdb.periodsModel" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: Vergi
  Date: 2017/6/2
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uotel - Add a Reservation</title>
</head>
<body>
<h1>Add a Reservation</h1><br>

We now have the following THs:<br>

<%
    int valid = 0;
    thModel thmodel = new thModel();
    ResultSet res = null;
    res = thmodel.getAll();
    while   (res.next()){
%>
ID <%=res.getInt("id")%>: <%=res.getString("housename")%>, at price <%=res.getInt("price")%>.<br>
<%
    }
%>

<br>You can now select a TH ID
<form action="addres.jsp" method="get">
    <div><label>TH ID <input name="thid" type="text"></label></div>
    <div><input type="submit"></div>
</form>

<%
    String houseid;
    if (request.getParameter("thid") != null){
        houseid = request.getParameter("thid");
    }
    else{
        houseid = (String) session.getAttribute("houseid");
    }
    //System.out.println("houseid=" + houseid);
    if (houseid != null){
        valid = 1;
        try {
            Integer.parseInt(houseid);
        }
        catch (NumberFormatException n) {
            valid = 0;
        }
        if (valid == 1){
%>
            <br>The selected TH has the following available period:<br>
<%
            session.setAttribute("houseid", houseid);
            periodsModel pmodel = new periodsModel();
            res = pmodel.query(Integer.parseInt(houseid));
            while   (res.next()){
%>
                ID <%=res.getInt("id")%>: from <%=res.getDate("starttime")%> to <%=res.getDate("endtime")%>.<br>
<%
            }
%>
            <br><br>You can now select an available period:<br>
            <form action="addres.jsp" method="get">
                <div><label>Period ID <input name="periodid" type="text"></label></div>
                <div><input type="submit"></div>
            </form>
<%
            String periodid;
            if (request.getParameter("periodid") != null){
                periodid = request.getParameter("periodid");
            }
            else{
                periodid = (String) session.getAttribute("periodid");
            }
            System.out.println("periodid=" + periodid);
            if (periodid != null) {
                valid = 1;
                try {
                    Integer.parseInt(periodid);
                }
                catch (NumberFormatException n) {
                    valid = 0;
                }
                if  (valid == 1){
                    //System.out.println("lalala");
                    session.setAttribute("periodid", periodid);
                    resModel model = null;
                    model = new resModel();
                    int resId = 0;
                    try {
                        resId = model.addres((Integer) session.getAttribute("userid"), Integer.parseInt(houseid), Integer.parseInt(periodid));
                    } catch (SQLException e) {
                        e.printStackTrace();
%>
                        <strong>Error.</strong>
<%
                    }
                    if  (resId != 0){
                        session.setAttribute("houseid", null);
                        session.setAttribute("periodid", null);
                        Set<Integer> advice = model.suggest((Integer) session.getAttribute("userid"), Integer.parseInt(houseid), Integer.parseInt(periodid));
%>
                        <strong>Reservation succeeded! </strong> Your reservation id is <%=resId%>.<br>
<%
                        if  (advice != null){
%>
                            We now have the following TH suggestions for you:
<%
                            for(Integer index : advice){
%>
                                <%=index%>
<%
                            }
%>
                                <br>
<%
                        }
                    }
                }
                else{
%>
                    Period ID must be a number!<br>
<%
                 }
            }
    } // for valid thid
    else{
%>
        TH id must be a number!<br>
<%
    }
    }
%>
<br><br>
<a href="index.jsp">back</a>
</body>
</html>
