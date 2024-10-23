/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.sql.SQLException;
import model.User;
import util.DatabaseConnection;

public class UserRegistrationController {

    public void registerUser(User user) throws SQLException {
        UserController userController = new UserController();
        userController.addUser(user);
    }
}