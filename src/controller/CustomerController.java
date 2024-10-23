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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import util.DatabaseConnection;

public class CustomerController {

    public void addCustomer(Customer customer) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO customers (name, cpf_cnpj, birthdate, address, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getCpfCnpj());
        stmt.setDate(3, Date.valueOf(customer.getBirthdate()));
        stmt.setString(4, customer.getAddress());
        stmt.setString(5, customer.getPhone());
        stmt.setString(6, customer.getEmail());
        stmt.executeUpdate();
    }

    public void updateCustomer(int id, String name, String cpfCnpj, LocalDate birthdate, String address, String phone,
            String email) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE customers SET name = ?, cpf_cnpj = ?, birthdate = ?, address = ?, phone = ?, email = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, cpfCnpj);
        stmt.setDate(3, Date.valueOf(birthdate));
        stmt.setString(4, address);
        stmt.setString(5, phone);
        stmt.setString(6, email);
        stmt.setInt(7, id);
        stmt.executeUpdate();
    }

    public void deleteCustomer(int id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM customers WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public List<Customer> getAllCustomers() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM customers";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("cpf_cnpj"),
                    rs.getDate("birthdate").toLocalDate(),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("email"));
            customers.add(customer);
        }
        return customers;
    }

}