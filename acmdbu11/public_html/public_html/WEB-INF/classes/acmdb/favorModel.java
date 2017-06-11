package acmdb;

import java.sql.*;

/**
 * Created by Vergi on 2017/6/3.
 */
public class favorModel {
    private Connection con;

    public favorModel() throws SQLException, ClassNotFoundException {
        String dbURL = "jdbc:mysql://localhost:3306/acmdb11";
        String username = "acmdbu11";
        String passwd = "eq2tosjt";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dbURL, username, passwd);
    }

    public int addfavors(int userid, int thid) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("SELECT id FROM favors WHERE userid = ? AND thid = ?", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, userid);
        stmt.setInt(2, thid);
        ResultSet trs = stmt.executeQuery();
        if (trs.next()) return  trs.getInt(1);
        stmt = con.prepareStatement("INSERT INTO favors (userid, thid) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, userid);
        stmt.setInt(2, thid);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next())  return  rs.getInt(1);
        else    return  -1;
    }

    public ResultSet query(int userid) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("SELECT thid FROM favors where userid=" + String.valueOf(userid));
        return stmt.executeQuery();
    }
}
