/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/airline_management"; // URL do banco de dados
    private static final String USER = "admin_voos"; // Usuário do banco de dados (substitua por seu usuário)
    private static final String PASSWORD = "senha123"; // Senha do banco de dados (substitua pela sua senha)

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            // Registrar o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estabelecer a conexão com o banco de dados
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
            throw new SQLException(e);
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }

        return connection;
    }

    // Método para fechar a conexão com o banco de dados
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
