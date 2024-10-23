package controller;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Ticket;
import model.Flight;
import model.Customer;
import util.DatabaseConnection;

public class TicketController {

    public void addTicket(Ticket ticket) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO tickets (flight_id, customer_id, seat_number, baggage, total_price, ticket_pdf) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, ticket.getFlight().getId());
        stmt.setInt(2, ticket.getCustomer().getId());
        stmt.setString(3, ticket.getSeatNumber());
        stmt.setInt(4, ticket.getBaggage());
        stmt.setDouble(5, ticket.getTotalPrice());
        stmt.setBytes(6, ticket.getTicketPdf());
        stmt.executeUpdate();
    }

    public List<Ticket> getAllTickets() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT t.id, t.seat_number, t.baggage, t.total_price, t.ticket_pdf, " +
                "f.id AS flight_id, f.flight_number, " +
                "c.id AS customer_id, c.name " +
                "FROM tickets t " +
                "JOIN flights f ON t.flight_id = f.id " +
                "JOIN customers c ON t.customer_id = c.id";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Ticket> tickets = new ArrayList<>();
        while (rs.next()) {
            Flight flight = new Flight();
            flight.setId(rs.getInt("flight_id"));
            flight.setFlightNumber(rs.getString("flight_number"));

            Customer customer = new Customer();
            customer.setId(rs.getInt("customer_id"));
            customer.setName(rs.getString("name"));

            Ticket ticket = new Ticket(
                    rs.getInt("id"),
                    flight,
                    customer,
                    rs.getString("seat_number"),
                    rs.getInt("baggage"),
                    rs.getDouble("total_price"),
                    rs.getBytes("ticket_pdf"));
            tickets.add(ticket);
        }
        return tickets;
    }
}