package testThemes.je.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import testThemes.je.jdbc.dto.TicketFilter;
import testThemes.je.jdbc.entity.Ticket;
import testThemes.je.jdbc.exception.DaoException;
import testThemes.je.jdbc.utils.ConnectionManager;

public class TicketDao implements Dao<Long, Ticket> {
    private final static TicketDao INSTANCE = new TicketDao();
    private static final FlightDao flightDao = FlightDao.getInstance();

    private final static String SAVE_SQL =
            "INSERT INTO ticket" +
            "(passenger_no, passenger_name, flight_id, seat_no, cost) " +
            "VALUES (?, ?, ?, ?, ?)";

    public Ticket save(Ticket ticket) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ticket.getPassangerNo());
            statement.setString(2, ticket.getPassangerName());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                ticket.setId(keys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ticket;
    }

    private static final String DELETE_SQL =
            "DELETE FROM ticket " +
            "WHERE id = ?";

    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static final String FIND_ALL_SQL =
//            "SELECT * FROM ticket";
            "SELECT t.id, t.passenger_no, t.passenger_name, t.flight_id, t.seat_no, t.cost " +
            "f.flight_no, f.departure_date, f.departure_airport_code, f.arrival_date, f.arrival_airport_code, f.aircraft_id, f.status " +
            "FROM ticket t " +
            "JOIN flight f ON t.flight_id = f.id";

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                tickets.add(
                        buildTicket(result)
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tickets;
    }

    private static Ticket buildTicket(ResultSet result) throws SQLException {
//        Flight flight = new Flight(
//                result.getLong("flight_id"),
//                result.getString("flight_no"),
//                result.getTimestamp("departure_date").toLocalDateTime(),
//                result.getString("departure_airport_code"),
//                result.getTimestamp("arrival_date").toLocalDateTime(),
//                result.getString("arrival_airport_code"),
//                result.getInt("aircraft_id"),
//                FlightStatus.valueOf(result.getString("status"))
//        );
        return new Ticket(result.getLong("id"),
                result.getString("passenger_no"),
                result.getString("passenger_name"),
                flightDao.findById(
                        result.getLong("flight_id"),
                        result.getStatement().getConnection()
                ).orElse(null),
                result.getString("seat_no"),
                result.getBigDecimal("cost")
        );
    }

    private static final String FIND_BY_ID_SQL =
//            "SELECT * FROM ticket";
            "SELECT id, passenger_no, passenger_name, flight_id, seat_no, cost FROM ticket WHERE id = ?;";


    public Optional<Ticket> findById(Long id) {
        Ticket ticket = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ticket = buildTicket(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(ticket);
    }

    private static final String UPDATE_SQL =
            "UPDATE ticket " +
            "SET passenger_no = ?, " +
            "    passenger_name = ?, " +
            "    flight_id = ?," +
            "    seat_no = ?, " +
            "    cost = ? " +
            "WHERE id = ?";

    public boolean update(Ticket ticket) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, ticket.getPassangerNo());
            statement.setString(2, ticket.getPassangerName());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.setLong(6, ticket.getId());
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public List<Ticket> findAll(TicketFilter ticketFilter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (ticketFilter.passangerName() != null) {
            parameters.add(ticketFilter.passangerName());
            whereSql.add("passenger_name = ?");
        }
        if (ticketFilter.seatNo() != null) {
            parameters.add("%" + ticketFilter.seatNo() + "%");
            whereSql.add("seat_no like ?");
        }
        parameters.add(ticketFilter.limit());
        parameters.add(ticketFilter.offset());
        String where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : " ",
                " LIMIT ? OFFSET ? "
        ));

        String sql = FIND_ALL_SQL + where;
        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(statement);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                tickets.add(
                        buildTicket(result)
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tickets;
    }



    public static TicketDao getInstance() {
        return INSTANCE;
    }

    private TicketDao() {
    }
}
