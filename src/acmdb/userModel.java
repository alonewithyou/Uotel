package acmdb;

import java.sql.*;
import java.lang.String;

/**
 * Created by Vergi on 2017/5/31.
 */
public class userModel {
    private Connection con;

    public userModel() throws SQLException, ClassNotFoundException {
        String dbURL = "jdbc:mysql://localhost:3306/acmdb11?useSSL=false";
        String username = "acmdbu11";
        String passwd = "eq2tosjt";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dbURL, username, passwd);
    }

    public int getUserid(String username) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT id FROM users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) System.out.println("Damn.");
        return  rs.getInt(1);
    }

    public int registerUser(String username, String passwd, String fullname, String address, String phone) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO users (username, passwd, fullname, address, phone) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, username);
        stmt.setString(2, passwd);
        stmt.setString(3, fullname);
        stmt.setString(4, address);
        stmt.setString(5, phone);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next())  return  rs.getInt(1);
        else    return  -1;
    }

    public int checkLogin(String username, String password) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE username = ?", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String expect = rs.getString("passwd");
            if (expect.equals(password))
                return rs.getInt("id");
        }
        return  -1;
    }

    public boolean judge(int userid1, int userid2) throws SQLException {
        PreparedStatement stmt1 = con.prepareStatement("SELECT * FROM favors WHERE userid = ?", Statement.RETURN_GENERATED_KEYS);
        stmt1.setInt(1, userid1);
        ResultSet rs1 = stmt1.executeQuery();
        PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM favors WHERE userid = ?", Statement.RETURN_GENERATED_KEYS);
        stmt2.setInt(1, userid2);
        while(rs1.next()){
            ResultSet rs2 = stmt2.executeQuery();
            while(rs2.next()){
                if(rs1.getInt(3) == rs2.getInt(3)) return true;
            }
        }
        return false;
    }
    public int degrees(int userid1, int userid2) throws SQLException {
        PreparedStatement stmt1 = con.prepareStatement("SELECT * FROM users WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
        stmt1.setInt(1, userid1);
        ResultSet rs1 = stmt1.executeQuery();
        if(!rs1.next()) return -2;
        PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM users WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
        stmt2.setInt(1, userid2);
        ResultSet rs2 = stmt2.executeQuery();
        if(!rs2.next()) return -2;
        if(judge(userid1, userid2)) return 1;
        PreparedStatement stmt = con.prepareStatement("SELECT id FROM users", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            if(judge(rs.getInt(1), userid1)){
                if (judge(rs.getInt(1), userid2))
                    return 2;
            }
        }
        return -1;
    }
}
