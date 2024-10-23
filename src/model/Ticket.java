/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author JoelDeAriovaldo
 */

public class Ticket {
    private int id;
    private Flight flight;
    private Customer customer;
    private String seatNumber;
    private int baggage;
    private double totalPrice;
    private byte[] ticketPdf; // Para armazenar o PDF do bilhete

    public Ticket(int id, Flight flight, Customer customer, String seatNumber, int baggage, double totalPrice,
            byte[] ticketPdf) {
        this.id = id;
        this.flight = flight;
        this.customer = customer;
        this.seatNumber = seatNumber;
        this.baggage = baggage;
        this.totalPrice = totalPrice;
        this.ticketPdf = ticketPdf;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getBaggage() {
        return baggage;
    }

    public void setBaggage(int baggage) {
        this.baggage = baggage;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public byte[] getTicketPdf() {
        return ticketPdf;
    }

    public void setTicketPdf(byte[] ticketPdf) {
        this.ticketPdf = ticketPdf;
    }
}
