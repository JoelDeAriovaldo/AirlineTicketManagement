package model;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.time.LocalDate;

public class Customer {
    private int id;
    private String name;
    private String cpfCnpj;
    private LocalDate birthdate;
    private String address;
    private String phone;
    private String email;

    public Customer(int id, String name, String cpfCnpj, LocalDate birthdate, String address, String phone,
            String email) {
        this.id = id;
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.birthdate = birthdate;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Default constructor
    public Customer() {
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name;
    }
}