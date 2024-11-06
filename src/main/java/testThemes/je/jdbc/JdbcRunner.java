package testThemes.je.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import testThemes.je.jdbc.utils.ConnectionManager;

public class JdbcRunner {

	public static void main(String[] args) throws SQLException {
		
		String sql = "CREATE SCHEMA game; ";
		String sqlQuery = "SELECT * FROM ticket;";
//		String sql = "DROP SCHEMA game; ";

		try (Connection connection = ConnectionManager.open(); 
				Statement statement = connection.createStatement()) {
			System.out.println(statement.execute(sql));
			ResultSet result = statement.executeQuery(sqlQuery);
			while(result.next())
				System.out.println(result.getLong("id"));
				System.out.println(result.getLong("flight_id"));
				System.out.println(result.getLong("cost"));
				System.out.println(result.getString("passanger_name"));
				System.out.println(result.getObject("id"));
		}
		System.out.println(getTicketsByFlightId(8L));;
	}
	
	public static List<Long> getTicketsByFlightId(Long flightId) {
		List<Long> tickets = new ArrayList<Long>();
		String sql = "SELECT * FROM ticket WHERE flight_id = ?";
		try (Connection connection = ConnectionManager.open();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, flightId);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				tickets.add(result.getLong("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tickets;
	}

}
