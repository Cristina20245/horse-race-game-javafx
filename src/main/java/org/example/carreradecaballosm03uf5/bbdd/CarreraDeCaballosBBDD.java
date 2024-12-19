/*package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CarreraDeCabballosBBDD {
}*/

package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CarreraDeCaballosBBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    // Método para crear la base de datos si no existe
    public void crearBaseDeDatos() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS carreradecaballos";
            stmt.executeUpdate(sql);
            System.out.println("Base de datos creada o ya existe.");
        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    // Método para obtener la conexión (ahora con la base de datos específica)
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL + "carreradecaballos", USER, PASSWORD);
        }
        return connection;
    }

    // Método para ejecutar una consulta SQL
    public void ejecutarConsulta(String sql) {
        try (Connection conn = getConnection()) {
            conn.createStatement().executeUpdate(sql);
            System.out.println("Consulta ejecutada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    // Método para conectar a la base de datos y manejarlo en un hilo separado
    public void conectarYEjecutar() {
        // Crear un hilo para la conexión a la base de datos
        Thread dbThread = new Thread(() -> {
            try {
                // Intentamos realizar la conexión a la base de datos
                try (Connection conn = getConnection()) {
                    System.out.println("Conexión exitosa a la base de datos");
                    // Puedes realizar otras operaciones con la base de datos aquí
                } catch (SQLException e) {
                    System.out.println("Error al conectar a la base de datos: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        });

        dbThread.setDaemon(true);  // Esto asegura que el hilo se cierre cuando la aplicación termine
        dbThread.start();  // Iniciar el hilo
    }

    public static void main(String[] args) {
        CarreraDeCaballosBBDD bd = new CarreraDeCaballosBBDD();
        bd.crearBaseDeDatos(); // Crear la base de datos si no existe
        bd.conectarYEjecutar(); // Conectar a la base de datos y realizar la consulta
    }
}
