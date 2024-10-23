/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author JoelDeAriovaldo
 */

public class User {
    private int id;
    private String name;
    private String email;
    private String password; // A senha deve ser criptografada antes de ser armazenada
    private String accessLevel;

    public User(int id, String name, String email, String password, String accessLevel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    // Add this constructor
    public User(String name, String email, String password, String accessLevel) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}