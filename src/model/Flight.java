package model;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.time.LocalDateTime;

public class Flight {
    private int id;
    private String airline;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String flightNumber;
    private String aircraftModel;
    private int availableSeats;
    private double ticketPrice;

    public Flight(int id, String airline, String origin, String destination, LocalDateTime departureTime,
            LocalDateTime arrivalTime, String flightNumber, String aircraftModel, int availableSeats,
            double ticketPrice) {
        this.id = id;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightNumber = flightNumber;
        this.aircraftModel = aircraftModel;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }

    public Flight(String airline, String origin, String destination, LocalDateTime departureTime,
            LocalDateTime arrivalTime, String flightNumber, String aircraftModel, int availableSeats,
            double ticketPrice) {
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightNumber = flightNumber;
        this.aircraftModel = aircraftModel;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }

    // Default constructor
    public Flight() {
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return flightNumber + " - " + origin + " to " + destination;
    }
}