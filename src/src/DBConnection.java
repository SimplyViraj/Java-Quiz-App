package src;

import java.sql.*;

public class DBConnection {
	 private static Connection conn;

	 public static Connection getConnection() throws SQLException {
		    if (conn == null || conn.isClosed()) 
		    {
		        try
		        {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		        } 
		        catch (ClassNotFoundException e) 
		        {
		            System.out.println("MySQL JDBC Driver not found!");
		            e.printStackTrace();
		        }

		        conn = DriverManager.getConnection(
		                "jdbc:mysql://localhost:3306/quizz",
		                "root",
		                "Praneetha@17"
		        );
		    }
		    return conn;
		}

}
