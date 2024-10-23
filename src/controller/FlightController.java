/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Flight;
import util.DatabaseConnection;

public class FlightController {

    public void addFlight(Flight flight) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO flights (airline, origin, destination, departure_time, arrival_time, flight_number, aircraft_model, available_seats, ticket_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, flight.getAirline());
        stmt.setString(2, flight.getOrigin());
        stmt.setString(3, flight.getDestination());
        stmt.setTimestamp(4, Timestamp.valueOf(flight.getDepartureTime()));
        stmt.setTimestamp(5, Timestamp.valueOf(flight.getArrivalTime()));
        stmt.setString(6, flight.getFlightNumber());
        stmt.setString(7, flight.getAircraftModel());
        stmt.setInt(8, flight.getAvailableSeats());
        stmt.setDouble(9, flight.getTicketPrice());
        stmt.executeUpdate();
    }

    public List<Flight> searchFlights(String origin, String destination) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM flights WHERE origin = ? AND destination = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, origin);
        stmt.setString(2, destination);
        ResultSet rs = stmt.executeQuery();

        List<Flight> flights = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String airline = rs.getString("airline");
            String flightNumber = rs.getString("flight_number");
            LocalDateTime departureTime = rs.getTimestamp("departure_time").toLocalDateTime();
            LocalDateTime arrivalTime = rs.getTimestamp("arrival_time").toLocalDateTime();
            String aircraftModel = rs.getString("aircraft_model");
            int availableSeats = rs.getInt("available_seats");
            double ticketPrice = rs.getDouble("ticket_price");

            Flight flight = new Flight(id, airline, origin, destination, departureTime, arrivalTime, flightNumber,
                    aircraftModel, availableSeats, ticketPrice);
            flights.add(flight);
        }

        return flights;
    }

    public List<Flight> getAllFlights() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM flights";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Flight> flights = new ArrayList<>();
        while (rs.next()) {
            Flight flight = new Flight(
                    rs.getInt("id"),
                    rs.getString("airline"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getTimestamp("departure_time").toLocalDateTime(),
                    rs.getTimestamp("arrival_time").toLocalDateTime(),
                    rs.getString("flight_number"),
                    rs.getString("aircraft_model"),
                    rs.getInt("available_seats"),
                    rs.getDouble("ticket_price"));
            flights.add(flight);
        }
        return flights;
    }
}
