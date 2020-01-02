package main;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import com.ibatis.common.jdbc.ScriptRunner;

public class Main {

	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	final static String DB_URL = "jdbc:mysql://localhost/";

	final static String USER = "username";
	final static String PASS = "password";

	public static void main(String[] args) {
		createDB();
	}

	private static void createDB() {
		Connection conn = null;
		Statement stmt = null;
		try{

			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			//Initialize the script runner
			ScriptRunner sr = new ScriptRunner(conn, false, false);
			//Creating a reader object
			Reader reader = new BufferedReader(new FileReader("/Files/Brew Day!.sql"));
			//Running the script
			sr.runScript(reader);			

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
}
