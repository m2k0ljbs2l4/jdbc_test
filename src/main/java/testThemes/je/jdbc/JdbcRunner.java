package testThemes.je.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcRunner {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "aboba";
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			connection.getTransactionIsolation();
		}
	}

}
