package acmdb;

import javafx.util.Pair;

import java.sql.*;
import java.util.*;

/**
 * Created by Vergi on 2017/6/2.
 */
public class resModel {
    private Connection con;

    public resModel() throws SQLException, ClassNotFoundException {
        String dbURL = "jdbc:mysql://localhost:3306/acmdb11?useSSL=false";
        String username = "acmdbu11";
        String passwd = "eq2tosjt";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dbURL, username, passwd);
    }

    public int addres(int userid, int thid, int periodid) throws SQLException{
        PreparedStatement stmtuser = con.prepareStatement("SELECT * FROM users WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
        stmtuser.setInt(1, userid);
        ResultSet rsuser = stmtuser.executeQuery();
        if(!rsuser.next()) return -1;
        PreparedStatement stmtperiods = con.prepareStatement("SELECT * FROM periods WHERE id = ? and thid = ?", Statement.RETURN_GENERATED_KEYS);
        stmtperiods.setInt(1, periodid);
        stmtperiods.setInt(2, thid) ;
        ResultSet rsperiods = stmtuser.executeQuery();
        if(!rsperiods.next()) return -1;
        PreparedStatement stmt = con.prepareStatement("INSERT INTO reserves (userid, thid, periodid, stayed) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, userid);
        stmt.setInt(2, thid);
        stmt.setInt(3, periodid);
        stmt.setInt(4, 0);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next())  return  rs.getInt(1);
        else    return  -1;
    }


    public Set<Integer> suggest(int userid, int thid, int period) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM reserves WHERE userid = ? and thid = ? and periodid = ?", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, userid);
        stmt.setInt(2, thid);
        stmt.setInt(3, period);
        ResultSet rsuser = stmt.executeQuery();
        if(!rsuser.next()) return null;
        Set<Integer> result = new HashSet<Integer>();
        PreparedStatement stmtresult = con.prepareStatement("SELECT * FROM reserves WHERE thid = ?", Statement.RETURN_GENERATED_KEYS);
        stmtresult.setInt(1, thid);
        ResultSet rs = stmtresult.executeQuery();
        while(rs.next()){
            System.out.println("Enter while");
            int user = rs.getInt(2);
            PreparedStatement stmtth = con.prepareStatement("SELECT * FROM reserves WHERE userid = ?", Statement.RETURN_GENERATED_KEYS);
            stmtth.setInt(1, user);
            ResultSet rsth = stmtth.executeQuery();
            while(rsth.next()){
                result.add(rsth.getInt(3));
            }
        }
        if (result == null) System.out.println("result null");
        for(Integer index : result){
            int cnt = 0;
            PreparedStatement stmtcnt = con.prepareStatement("SELECT * FROM reserves WHERE thid = ?", Statement.RETURN_GENERATED_KEYS);
            stmtcnt.setInt(1, index);
            ResultSet rscnt = stmtcnt.executeQuery();
            if(rscnt.next()){
                if(rscnt.getInt("stayed") == 1) cnt++;
            }
            Pair<Integer, Integer> pair;
        }
        return result;
    }


    public ResultSet getres(int userid) throws SQLException {
        PreparedStatement stmtuser = con.prepareStatement("SELECT * FROM reserves WHERE userid = ? AND stayed = 0", Statement.RETURN_GENERATED_KEYS);
        stmtuser.setInt(1, userid);
        ResultSet rsuser = stmtuser.executeQuery();
        return rsuser;
    }

    public int visit(int rid) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM reserves WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, rid);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) return -1;
        String sql = "UPDATE reserves SET stayed=1 WHERE id=?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, rid);
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated;
    }
}
