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

    // Método para obtener o establecer la conexión a la base de datos
    public static Connection getConnection() {
        if (connection == null) {
            try {
                crearBaseDeDatos();
                connection = DriverManager.getConnection(URL + "carreradecaballos", USER, PASSWORD);
                System.out.println("Conexión establecida con éxito a 'carreradecaballos'.");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    // Método para crear la base de datos
    public static void crearBaseDeDatos() {
        try (Connection tempConnection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = tempConnection.createStatement()) {

            String createDatabase = "CREATE DATABASE IF NOT EXISTS carreradecaballos;";
            stmt.executeUpdate(createDatabase);
            System.out.println("Base de datos 'carreradecaballos' creada o ya existe.");

        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    // Método genérico para ejecutar scripts SQL
    public static void ejecutarScript(String script) {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(script);
        } catch (SQLException e) {
            System.out.println("Error al ejecutar script SQL: " + e.getMessage());
        }
    }

    // Método para crear las tablas y manejar la lógica de idPartida
    public static void crearTablas() {
        // Crear base de datos y tablas si no existen
        crearBaseDeDatos();
        ejecutarScript("USE carreradecaballos;");

        // Crear la tabla 'partidas'
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
            if (rs.next() && rs.getInt(1) > 0) {
                nuevoIdPartida = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el máximo idPartida: " + e.getMessage());
        }

        // Insertar nueva partida
        String insertarPartida = "INSERT INTO partidas (idPartida) VALUES (" + nuevoIdPartida + ")";
        ejecutarScript(insertarPartida);

        // Crear las tablas jugadores<N> y rondas<N>
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

        // Insertar jugadores
        //insertarJugadores(nuevoIdPartida, "Jugador 1", "Espada", 100, "Jugador 2", "Oros", 100);
        insertarRondas(nuevoIdPartida);

        System.out.printf("Tablas jugadores%d y rondas%d creadas con éxito.%n", nuevoIdPartida, nuevoIdPartida);
    }

    // Método para insertar jugadores
    private static void insertarJugadores(int idPartida) {
        String insertarJugador1 = """
            INSERT INTO jugadores%s (nombre, palo, bote, posicion, idPartida)
            VALUES ('Jugador 1', 'Espada', 100, 0, %d);
            """.formatted(idPartida, idPartida);
        String insertarJugador2 = """
            INSERT INTO jugadores%s (nombre, palo, bote, posicion, idPartida)
            VALUES ('Jugador 2', 'Oros', 100, 0, %d);
            """.formatted(idPartida, idPartida);

        ejecutarScript(insertarJugador1);
        ejecutarScript(insertarJugador2);
    }

    // Método para insertar rondas
    private static void insertarRondas(int idPartida) {
        String insertarRonda1 = """
            INSERT INTO rondas%s (idPartida, numRonda, numCarta, paloCarta)
            VALUES (%d, 1, 5, 'Espada');
            """.formatted(idPartida, idPartida);
        String insertarRonda2 = """
            INSERT INTO rondas%s (idPartida, numRonda, numCarta, paloCarta)
            VALUES (%d, 2, 7, 'Oros');
            """.formatted(idPartida, idPartida);

        ejecutarScript(insertarRonda1);
        ejecutarScript(insertarRonda2);
    }

    public static void main(String[] args) {
        crearTablas();
    }
}

