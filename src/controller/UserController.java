/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.User;
import util.DatabaseConnection;

public class UserController {

    public void addUser(User user) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO users (name, email, password, access_level) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPassword());
        stmt.setString(4, user.getAccessLevel());
        stmt.executeUpdate();
    }
}
