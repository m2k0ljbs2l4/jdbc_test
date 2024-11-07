package testThemes.je.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import testThemes.je.jdbc.dao.TicketDao;
import testThemes.je.jdbc.entity.Ticket;
import testThemes.je.jdbc.utils.ConnectionManager;

public class JdbcRunner {

	public static void main(String[] args) throws SQLException {
		TicketDao ticketDao = TicketDao.getInstance();
		Ticket ticket = new Ticket(null, null, null, null, null, null);
		ticket.setPassportNo("adf123");
		ticket.setPassangerName("Andrei");
		ticket.setFlightId(4L);
		ticket.setSeatNo("5B");
		ticket.setCost(BigDecimal.TEN);
		
		System.out.println(ticketDao.save(ticket));;
	}
}
