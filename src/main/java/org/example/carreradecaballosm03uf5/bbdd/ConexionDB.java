package org.example.carreradecaballosm03uf5.bbdd;

import org.example.carreradecaballosm03uf5.Utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static Connection connection;

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
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
        System.out.println("Conexión cerrada con éxito.");
    }
}