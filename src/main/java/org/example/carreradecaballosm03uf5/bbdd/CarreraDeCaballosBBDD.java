package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CarreraDeCaballosBBDD {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Método para establecer la conexión a la base de datos
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    // Método para ejecutar un script SQL
    public static void ejecutarScript(String script) {
        if (script != null && !script.trim().isEmpty()) {
            try (Statement stmt = getConnection().createStatement()) {
                stmt.executeUpdate(script);
                System.out.println("Script ejecutado con éxito: " + script);
            } catch (SQLException e) {
                System.out.println("Error al ejecutar el script: " + e.getMessage());
            }
        }
    }

    // Método para crear la base de datos si no existe
    public static void crearBaseDeDatos() {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS carreradecaballos;";
        ejecutarScript(createDatabase);
    }

    // Método para realizar todas las operaciones necesarias
    public static void crearTablas() {
        // Asegurarse de que la base de datos exista
        crearBaseDeDatos();

        // Seleccionar la base de datos
        ejecutarScript("USE carreradecaballos;");

        // Crear la tabla 'partidas' si no existe
        String createTablePartidas = """
            CREATE TABLE IF NOT EXISTS partidas (
                idPartida INT AUTO_INCREMENT PRIMARY KEY
            );
            """;
        ejecutarScript(createTablePartidas);

        // Obtener el máximo idPartida existente
        int nuevoIdPartida = 1;
        try (Statement stmt = getConnection().createStatement()) {
            var rs = stmt.executeQuery("SELECT MAX(idPartida) FROM partidas");
            if (rs.next()) {
                nuevoIdPartida = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el máximo idPartida: " + e.getMessage());
        }

        // Insertar una nueva partida
        String insertarPartida = "INSERT INTO partidas (idPartida) VALUES (" + nuevoIdPartida + ")";
        ejecutarScript(insertarPartida);

        // Crear las tablas jugadoresN y rondasN donde N es el nuevo idPartida
        String createTableJugadores = """
            CREATE TABLE IF NOT EXISTS jugadores%s (
                idJugador INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(255) NOT NULL,
                palo VARCHAR(50) NOT NULL,
                bote INT NOT NULL,
                posicion INT NOT NULL,
                idPartida INT,
                FOREIGN KEY (idPartida) REFERENCES partidas(idPartida)
            );
            """.formatted(nuevoIdPartida);

        String createTableRondas = """
            CREATE TABLE IF NOT EXISTS rondas%s (
                idPartida INT NOT NULL,
                numRonda INT NOT NULL,
                numCarta INT NOT NULL,
                paloCarta VARCHAR(50) NOT NULL,
                PRIMARY KEY (idPartida, numRonda),
                FOREIGN KEY (idPartida) REFERENCES partidas(idPartida)
            );
            """.formatted(nuevoIdPartida);

        ejecutarScript(createTableJugadores);
        ejecutarScript(createTableRondas);

        // Aquí se insertan jugadores y rondas con el idPartida correspondiente
        insertarJugadores(nuevoIdPartida);
        insertarRondas(nuevoIdPartida);

        System.out.printf("Tablas jugadores%d y rondas%d creadas con éxito.%n", nuevoIdPartida, nuevoIdPartida);
    }

    // Método para insertar jugadores en la tabla de jugadores
    public static void insertarJugadores(int idPartida) {
        String insertarJugador1 = "INSERT INTO jugadores" + idPartida + " (nombre, palo, bote, posicion, idPartida) VALUES ('Jugador 1', 'Espada', 1000, 1, " + idPartida + ");";
        String insertarJugador2 = "INSERT INTO jugadores" + idPartida + " (nombre, palo, bote, posicion, idPartida) VALUES ('Jugador 2', 'Oros', 1000, 2, " + idPartida + ");";
        ejecutarScript(insertarJugador1);
        ejecutarScript(insertarJugador2);
    }

    // Método para insertar rondas en la tabla de rondas
    public static void insertarRondas(int idPartida) {
        String insertarRonda1 = "INSERT INTO rondas" + idPartida + " (numRonda, numCarta, paloCarta, idPartida) VALUES (1, 5, 'Espada', " + idPartida + ");";
        String insertarRonda2 = "INSERT INTO rondas" + idPartida + " (numRonda, numCarta, paloCarta, idPartida) VALUES (2, 7, 'Oros', " + idPartida + ");";
        ejecutarScript(insertarRonda1);
        ejecutarScript(insertarRonda2);
    }

    // Método principal para probar la funcionalidad
    public static void main(String[] args) {
        getConnection(); // Establecer la conexión
        crearTablas(); // Realizar todas las operaciones necesarias
    }

    public void conectarYEjecutar() {
    }
}
