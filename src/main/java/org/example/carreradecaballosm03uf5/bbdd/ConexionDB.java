package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/carreradecaballos";  // Usar el nombre de la base de datos
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida con éxito.");
            } catch (SQLException e) {
                throw new SQLException("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}