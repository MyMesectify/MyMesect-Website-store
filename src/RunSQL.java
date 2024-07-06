import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class RunSQL {
	
	private String location;
	private String username;
	private String password;

	public RunSQL(String className,
			String location,
			String username,
			String password) 
	throws ClassNotFoundException {
		// TODO Auto-generated constructor stub
		
		Class.forName(className);
		this.location = location;
		this.username = username;
		this.password = password;
		
	}

	public Connection getConnection() 
	throws SQLException {
		
		return DriverManager.getConnection(
				location, 
				username, 
				password);
	}
	
}
