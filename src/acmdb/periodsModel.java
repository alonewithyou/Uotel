package acmdb;

import java.sql.*;

/**
 * Created by zhangjiaheng on 17/6/3.
 */
public class periodsModel {
    private Connection con;

    public periodsModel() throws SQLException, ClassNotFoundException {
        String dbURL = "jdbc:mysql://localhost:3306/acmdb11?useSSL=false";
        String username = "acmdbu11";
        String passwd = "eq2tosjt";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dbURL, username, passwd);
    }

    public ResultSet getAll() throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT id, starttime, endtime, thid FROM periods WHERE id NOT IN (SELECT periodID FROM reserves)");
        return stmt.executeQuery();
    }

    public int addperiods(int thid, Date starttime, Date endtime) throws SQLException{
        PreparedStatement stmtuser = con.prepareStatement("SELECT * FROM ths WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
        stmtuser.setInt(1, thid);
        ResultSet rsuser = stmtuser.executeQuery();
        if(!rsuser.next()) return -2;
        PreparedStatement stmt = con.prepareStatement("INSERT INTO periods (thid, starttime, endtime) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, thid);
        stmt.setDate(2, starttime);
        stmt.setDate(3, endtime);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next())  return  rs.getInt(1);
        else    return  -1;
    }

    public ResultSet query(int thid) throws SQLException {
        //System.out.println("Querying: " + thid);
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT * FROM periods WHERE thid = " + String.valueOf(thid)
                + " AND id not IN (SELECT periodid FROM reserves WHERE thid = " + String.valueOf(thid) + ")");
        return rs;
    }
}