package main;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import com.ibatis.common.jdbc.ScriptRunner;

public class Main {

	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private final static String DB_URL = "jdbc:mysql://localhost:3306/";

	private final static String USER = "root";
	private final static String PASS = "toor";
	
	public static Connection conn = null;

	public static void main(String[] args) {
		createDB();
	}

	public static void connectDB() {
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Database connected!");

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}

	private static void createDB() {
		connectDB();
		//Initialize the script runner
		ScriptRunner sr = new ScriptRunner(conn, false, false);
		//Creating a reader object
		Reader reader;
		try {
			reader = new BufferedReader(new FileReader("/Files/Brew Day!.sql"));
			sr.runScript(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}			
	}
}
