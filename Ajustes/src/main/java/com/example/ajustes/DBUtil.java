package com.example.ajustes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // Configuración de la conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/ProyectoIntegrado";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Método para establecer una conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para cerrar una conexión a la base de datos
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
