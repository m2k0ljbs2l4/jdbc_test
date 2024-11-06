package testThemes.je.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import testThemes.je.jdbc.utils.ConnectionManager;

public class JdbcRunner {

	public static void main(String[] args) throws SQLException {
		
		String sql = "create schema game; ";

		try (Connection connection = ConnectionManager.open(); 
				Statement statement = connection.createStatement()) {
			System.out.println(statement.execute(sql));;
		}
	}

}
