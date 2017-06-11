/**
 * Created by Vergi on 2017/6/4.
 */

import acmdb.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
This commandline support the following commands:
"login" with username and password
"register" a new user
"listall" tp list available periods for all THs
"addth" to add a new TH
"updateth" to update the information of some certain TH
"addperiod" to add a new available period for some TH
"reserve" to make a reservation
"record" to record a stay
"favor" to choose a favourite TH
"degree" to calculate the degree of separation between two users"
 */

public class client {

    private static int userid;

    private static void print(Object x){
        System.out.println(x);
    }

    private static void printHelp(){
        print("Available commands:");
        print("login {username} {password}");
        print("register {username} {password} [fullname] [address] [phone]");
        print("listall");
        print("addth {housename} [address] [url] [tel] {yearbuilt} {price}");
        print("update {thID} {housename} [address] [url] [tel] {yearbuilt} {price}");
        print("addperiod {thID} {yyyy-MM-DD} {yyyy-MM-DD}");
        print("reserve {thID} {periodID}");
        print("record {reservationID}");
        print("favor {thID}");
        print("degree {username1} {username2}");
        print("");
        print("If you'd like to skip some optional field and fill in the follow-up ones, use 'null' to occupy the corresponding field.");
        //Print all available commands
    }

    private static int login(String[] args) throws SQLException, ClassNotFoundException {
        //Operate login
        if (args.length != 2){
            print("Syntax error.");
            return  0;
        }
        userModel umodel = new userModel();
        int uid = umodel.checkLogin(args[0], args[1]);
        if (uid == -1){
            print("Login failed. Please try again.");
            return  0;
        }
        else{
            print("You successfully login as uid=" + uid + ".");
            return  uid;
        }
    }

    private static int register(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length < 2 || args.length > 5){
            print("Syntax error.");
            return  0;
        }
        String[] paras = new String[5];
        for (int i = 0; i < 5; ++i)
            if (i < args.length && !Objects.equals(args[i], "null")){
                paras[i] = args[i];
            }
            else{
                paras[i] = null;
            }
        userModel umodel = new userModel();
        int uid = umodel.registerUser(paras[0], paras[1], paras[2], paras[3], paras[4]);
        if (uid == -1){
            print("Registration failed, please change your information.");
            return 0;
        }
        else{
            print("You have registered as uid=" + uid + " and login as this user.");
            return  uid;
        }
    }

    private static void listall() throws SQLException, ClassNotFoundException {
        periodsModel pmodel = new periodsModel();
        ResultSet periods = pmodel.getAll();
        int t = 0;
        while (periods.next()){
            t += 1;
            print("Period ID=" + periods.getInt("id") + ": TH " + periods.getInt("thid") + " available from " + periods.getDate("starttime") + " to " + periods.getDate("endtime"));
        }
        if (t == 0){
            print("No available periods at the moment.");
        }
        return  ;
    }

    private static void addth(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length < 3 || args.length > 6){
            print("Syntax error.");
            return  ;
        }
        String[] paras = new String[6];
        for (int i = 0; i < 6; ++i)
            if (i < args.length && !Objects.equals(args[i], "null")){
                paras[i] = args[i];
            }
            else{
                paras[i] = null;
            }
        thModel tmodel = new thModel();
        int thid = tmodel.addTH(paras[0], paras[1], paras[2], paras[3], Integer.parseInt(paras[4]), Integer.parseInt(paras[5]), userid);
        if (thid == -1){
            print("Addition failed, please check your information.");
        }
        else{
            print("Addition succeeded, new TH id=" + thid + ".");
        }
    }

    private static void updateth(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length < 5 || args.length > 8){
            print("Syntax error");
            return  ;
        }
        String[] paras = new String[7];
        for (int i = 0; i < 7; ++i)
            if (i < args.length && !Objects.equals(args[i], "null")){
                paras[i] = args[i];
            }
            else{
                paras[i] = null;
            }
        thModel tmodel = new thModel();
        try {
            int thid = tmodel.update(userid, Integer.parseInt(paras[0]), paras[1], paras[2], paras[3], paras[4], Integer.parseInt(paras[5]), Integer.parseInt(paras[6]));
        }
        catch (SQLException e){
            print("Update failed, please check your information. You can only update your th and must assign housename, yearbuilt and price.");
            return  ;
        }
        print("Update succeeded.");
    }

    private static void addperiod(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        if (args.length != 3){
            print("Syntax error.");
            return  ;
        }
        periodsModel pmodel = new periodsModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date stime = new java.sql.Date (sdf.parse(args[1]).getTime());
        java.sql.Date etime = new java.sql.Date (sdf.parse(args[2]).getTime());
        Scanner scan = new Scanner(System.in);
        while (true) {
            print("Please confirm your addition (y/n):");
            String tmp = scan.nextLine();
            if (Objects.equals(tmp, "y")){
                break;
            }
            if (Objects.equals(tmp, "n")){
                print("Addition cancelled.");
                return  ;
            }
        }
        int pid = pmodel.addperiods(Integer.parseInt(args[0]), stime, etime);
        if (pid == -1){
            print("Addition failed, please check your information.");
        }
        else{
            print("Addition succeeded, new period id=" + pid + ".");
        }
    }

    private static void reserve(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length != 2){
            print("Syntax error.");
            return  ;
        }
        resModel rmodel = new resModel();
        Scanner scan = new Scanner(System.in);
        while (true) {
            print("Please confirm your reservation (y/n):");
            String tmp = scan.nextLine();
            if (Objects.equals(tmp, "y")){
                break;
            }
            if (Objects.equals(tmp, "n")){
                print("Reservation cancelled.");
                return  ;
            }
        }
        int rid = rmodel.addres(userid, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        if  (rid == -1){
            print("Addition failed. Please check your information.");
        }
        else{
            print("Reservation succeed, rid=" + rid + ".");
            Set<Integer> advice = rmodel.suggest(userid, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            if (advice != null){
                print("We have the following suggestions for you:");
                for(Integer index : advice){
                    print(index + " ");
                }
                print("");
            }
        }
    }

    private static void record(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length != 1){
            print("Syntax error.");
            return  ;
        }
        resModel rmodel = new resModel();
        int rid = 0;
        try{
            rid = rmodel.visit(Integer.parseInt(args[0]));
        }catch (SQLException e){
            print("Recording failed. Please check your information.");
            return  ;
        }
        if (rid < 1){
            print("Recording failed. Please check your information.");
        }
        else{
            print("Recording succeeded.");
        }
    }

    private static void favor(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length != 1){
            print("Syntax error.");
            return  ;
        }
        favorModel fmodel = new favorModel();
        int fav = 0;
        try{
            fav = fmodel.addfavors(userid, Integer.parseInt(args[0]));
        }catch (SQLException e){
            print("Error.");
            return  ;
        }
        if (fav < 1){
            print("Error.");
        }
        else{
            print("Succeeded.");
        }
    }

    private static void degree(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length != 2){
            print("Syntax error.");
            return  ;
        }
        userModel umodel = new userModel();
        int uid1 = 0, uid2 = 0, res = 0;
        try{
            uid1 = umodel.getUserid(args[0]);
            uid2 = umodel.getUserid(args[1]);
            res = umodel.degrees(uid1, uid2);
        }catch (SQLException e){
            print("Error.");
            return  ;
        }
        print("The degree of separation between " + args[0] + " and " + args[1] + " is " + res + ".");
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        Scanner scan = new Scanner(System.in);
        userid = 0;

        print("Welcome to Uotel monitor");
        while (true){
            String input = scan.nextLine();
            String [] inputs = input.split("\\s+");
            String cmd = inputs [0];
            List<String> tmp = new ArrayList<String>();
            for (int i = 1; i < inputs.length; ++i) tmp.add(inputs[i]);
            String [] arguments = (String[]) tmp.toArray(new String[0]);
            switch (cmd){
                case "help":
                    printHelp();
                    break;
                case "login":
                    userid = login(arguments);
                    break;
                case "register":
                    userid = register(arguments);
                    break;
                case "addth":
                    if (userid == 0){
                        print("Please login first.");
                    }
                    else{
                        try {
                            addth(arguments);
                        }catch (Exception e){
                            print("Failed. Please check your input.");
                        }
                    }
                    break;
                case "updateth":
                    if (userid == 0){
                        print("Please login first.");
                    }
                    else{
                        try{
                        updateth(arguments);
                        } catch (Exception e){
                        print("Failed. Please check your input.");
                }
                    }
                    break;
                case "reserve":
                    if (userid == 0){
                        print("Please login first.");
                    }
                    else{
                        try {
                            reserve(arguments);
                        }catch (Exception e){
                            print("Failed. Please check your input.");
                        }
                    }
                    break;
                case "record":
                    if (userid == 0){
                        print("Please login first");
                    }
                    else{
                        try {
                            record(arguments);
                        }catch (Exception e){
                            print("Failed. Please check your input.");
                        }
                    }
                    break;
                case "favor":
                    if (userid == 0){
                        print("Please login first");
                    }
                    else{
                        try {
                            favor(arguments);
                        }catch (Exception e){
                            print("Failed. Please check your input.");
                        }
                    }
                    break;
                case "addperiod":
                    if (userid == 0){
                        print("Please login first");
                    }
                    else{
                        try {
                            addperiod(arguments);
                        }catch (Exception e){
                            print("Failed. Please check your input.");
                        }
                    }
                    break;
                case "degree":
                    try{
                        degree(arguments);
                    }catch (Exception e){
                        print("Failed. Please check your input.");
                    }
                    break;
                case "listall":
                    listall();
                    break;
                case "exit":
                    return  ;
                default:
                    print("No such command. Type 'help' for available commands.");
            }
        }
    }
}
