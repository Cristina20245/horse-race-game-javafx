package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class CarreraDeCaballosBBDD {

    // Cargar el controlador JDBC de MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Asegúrate de usar el controlador correcto
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String URL = "jdbc:mysql://localhost:3306/carreradecaballos";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    // Método para obtener la conexión
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public void ejecutarScript(String script) {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(script);  // Cambiar a executeUpdate
            System.out.println("Script ejecutado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al ejecutar el script: " + e.getMessage());
        }
    }

    // Método para ejecutar un script desde un archivo
    public void ejecutarScriptDesdeArchivo(String path) {
        StringBuilder script = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
            System.out.println("Ejecutando el script...");
            // Primero asegurarse de que estamos usando la base de datos correcta
            ejecutarScript("USE carreradecaballos;");
            // Ejecutar el script
            ejecutarScript(script.toString());
            verificarTablas();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo del script: " + e.getMessage());
        }
    }

    // Método para verificar las tablas en la base de datos
    private void verificarTablas() {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            String query = "SHOW TABLES"; // Consulta para mostrar las tablas
            var rs = stmt.executeQuery(query);

            System.out.println("Tablas en la base de datos:");
            while (rs.next()) {
                System.out.println(rs.getString(1)); // Imprime el nombre de cada tabla
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar las tablas: " + e.getMessage());
        }
    }

    // Método para crear la base de datos (si no existe)
    public void crearBaseDeDatos() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS carreradecaballos";
            stmt.executeUpdate(sql);
            System.out.println("Base de datos 'carreradecaballos' creada con éxito (si no existía).");
        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    // Método para eliminar las tablas si existen
    private void eliminarTablas() {
        String dropPartidas = "DROP TABLE IF EXISTS partidas";
        String dropJugadores = "DROP TABLE IF EXISTS jugadores";
        String dropRondas = "DROP TABLE IF EXISTS rondas";

        // Ejecutar las sentencias para eliminar las tablas
        ejecutarScript(dropPartidas);
        ejecutarScript(dropJugadores);
        ejecutarScript(dropRondas);
    }


    // Método para crear las tablas en la base de datos
    public void crearTablas() {
        eliminarTablas();

        String createTablePartidas = """
           CREATE TABLE IF NOT EXISTS partidas (
               idPartida INT AUTO_INCREMENT PRIMARY KEY
           );
           """;


        String createTableJugadores = """
           CREATE TABLE IF NOT EXISTS jugadores (
               idJugador INT AUTO_INCREMENT PRIMARY KEY,
               idPartida INT NOT NULL,
               nombre VARCHAR(255) NOT NULL,
               palo VARCHAR(50) NOT NULL,
               bote INT NOT NULL,
               posicion INT NOT NULL,
               FOREIGN KEY (idPartida) REFERENCES partidas(idPartida)
           );
           """;


        String createTableRondas = """
           CREATE TABLE IF NOT EXISTS rondas (
               idPartida INT NOT NULL,
               numRonda INT NOT NULL,
               numCarta INT NOT NULL,
               paloCarta VARCHAR(50) NOT NULL,
               PRIMARY KEY (idPartida, numRonda),
               FOREIGN KEY (idPartida) REFERENCES partidas(idPartida)
           );
           """;
        ejecutarScript(createTablePartidas);
        ejecutarScript(createTableJugadores);
        ejecutarScript(createTableRondas);
    }


    // Método para conectar y ejecutar la consulta en un hilo separado
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

    // Método principal para probar la conexión y ejecutar el script
    public static void main(String[] args) {
        CarreraDeCaballosBBDD bd = new CarreraDeCaballosBBDD();
        bd.crearBaseDeDatos();  // Crea la base de datos si no existe
        bd.crearTablas();  // Crear las tablas
        bd.ejecutarScriptDesdeArchivo("src/main/java/org/example/carreradecaballosm03uf5/scriptbbdd.sql"); // Ejecuta el script desde un archivo
        bd.conectarYEjecutar(); // Conectar a la base de datos en un hilo separado
    }
}


