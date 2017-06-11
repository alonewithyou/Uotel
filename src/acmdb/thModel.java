package acmdb;

import com.mysql.jdbc.UpdatableResultSet;

import java.sql.*;
import java.util.List;

/**
 * Created by Vergi on 2017/6/2.
 */
public class thModel {
    private Connection con;

    public thModel() throws SQLException, ClassNotFoundException {
        String dbURL = "jdbc:mysql://localhost:3306/acmdb11?useSSL=false";
        String username = "acmdbu11";
        String passwd = "eq2tosjt";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dbURL, username, passwd);
    }

    public int addTH(String housename, String address, String url, String tel, int year, int price, int ownerid) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("INSERT INTO ths (housename, address, url, tel, yearbuilt, price, ownerid) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, housename);
        stmt.setString(2, address);
        stmt.setString(3, url);
        stmt.setString(4, tel);
        stmt.setInt(5, year);
        stmt.setInt(6, price);
        stmt.setInt(7, ownerid);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next())  return  rs.getInt(1);
        else    return  0;
    }

    public ResultSet getTHList(int ownerid) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("SELECT id, housename, price FROM ths where ownerid=" + String.valueOf(ownerid));
        return stmt.executeQuery();
    }

    public ResultSet getAll() throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT id, housename, price FROM ths");
        return stmt.executeQuery();
    }

    public int update(int ownerid, int id, String housename, String address, String url, String tel, int year, int price) throws SQLException{
        String sql = "UPDATE ths SET housename=?, address=?, url=?, tel=?, yearbuilt=?, price=? WHERE id=? and ownerid = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, housename);
        statement.setString(2, address);
        statement.setString(3, url);
        statement.setString(4, tel);
        statement.setInt(5, year);
        statement.setInt(6, price);
        statement.setInt(7, id);
        statement.setInt(8, ownerid);
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated;
    }
}
