package testThemes.je.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import testThemes.je.jdbc.entity.Ticket;
import testThemes.je.jdbc.exception.DaoException;
import testThemes.je.jdbc.utils.ConnectionManager;

public class TicketDao {
	private final static TicketDao INSTANCE = new TicketDao();
	
	private final static String SAVE_SQL = 
			"INSERT INTO  ticket "
			+ "(passport_no, passanger_name, flight_id, seat_no, cost)"
			+ "VALUES (?, ?, ?, ?, ?)";
	
	private final static String DELETE_SQL = 
			"DELETE FROM  ticket "
			+ "WHERE id = ?";
	
	public Ticket save(Ticket ticket) {
		try (Connection connection = ConnectionManager.get();
				PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, ticket.getPassportNo());
			statement.setString(2, ticket.getPassangerName());
			statement.setLong(3, ticket.getFlightId());
			statement.setString(4, ticket.getSeatNo());
			statement.setBigDecimal(5, ticket.getCost());
			
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			if (keys.next())
				ticket.setId(keys.getLong("id"));
			return ticket;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	public boolean delete(Long id) {
		try (Connection connection = ConnectionManager.get();
				PreparedStatement statement = connection.prepareStatement(DELETE_SQL)){
			statement.setLong(1, id);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	
	public static TicketDao getInstance() {
		return INSTANCE;
	}

	private TicketDao() {
		
	}
	
}
