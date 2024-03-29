package acmdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.jcraft.jsch.JSch; // YOU'LL NEED jsch-0.1.54.jar
import com.jcraft.jsch.Session;

public class Connectorssh
{
	public Statement stmt;

	public Connection conn = null;
	public Session session = null;

	public Connectorssh() throws Exception
	{
		String sshHost = "georgia.eng.utah.edu";
		String sshUser = "YOUR SSH USERNAME";
		String sshPassword = "YOUR SSH PASSWORD";

		int lport = 5656;
		String rhost = "localhost";
		int rport = 3306;

		// ?allowMultiQueryies=true this allows for multiple queries to be sent in one string // YOU MAY NOT WANT THIS FUNCTIONALITY
		String url = "jdbc:mysql://localhost:" + lport + "/YOUR DB HERE?allowMultiQueries=true";
		String dbUser = "acmuser";
		String dbPassword = "acmspring17";
		String driverName = "com.mysql.jdbc.Driver";

		try
		{
			// Set StrictHostKeyChecking property to no to avoid UnknownHostKey
			// issue
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			session = jsch.getSession(sshUser, sshHost, 22);
			session.setPassword(sshPassword);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");

			int assigned_port = session.setPortForwardingL(lport, rhost, rport);
			System.out.println("localhost:" + assigned_port + " -> " + rhost + ":" + rport);
			System.out.println("Port Forwarded");
		
			// mysql database connectivity
			Class.forName(driverName).newInstance();
			conn = DriverManager.getConnection(url, dbUser, dbPassword);
			stmt = conn.createStatement();

			System.out.println("Database connection established");
			System.out.println("DONE");
		} catch (SQLException sql)
		{
			sql.printStackTrace();
			throw sql;
		}
	}

	public void closeConnection() throws Exception
	{
		conn.close();
		session.disconnect();
	}
}
