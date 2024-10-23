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
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import util.DatabaseConnection;

public class LoginController {

    public User login(String email, String password) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String accessLevel = rs.getString("access_level");
            return new User(id, name, email, password, accessLevel);
        }
        return null; // Login falhou
    }
}
