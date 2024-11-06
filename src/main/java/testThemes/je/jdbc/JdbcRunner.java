package testThemes.je.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import testThemes.je.jdbc.utils.ConnectionManager;

public class JdbcRunner {

	public static void main(String[] args) throws SQLException {

		try (Connection connection = ConnectionManager.open()) {
			System.out.println(connection.getTransactionIsolation());;
		}
	}

}
