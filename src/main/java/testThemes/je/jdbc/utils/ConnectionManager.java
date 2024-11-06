package testThemes.je.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static final String URL_KEY = "db.url";
	private static final String USERNAME_KEY = "db.username";
	private static final String PASSWORD_KEY = "db.password";
	
	public static Connection open() {
		try {
			return DriverManager.getConnection(PropetriesUtil.get(URL_KEY), USERNAME_KEY, PASSWORD_KEY);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ConnectionManager() {
		
	}
	
}
