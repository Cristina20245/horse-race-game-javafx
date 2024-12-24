package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraDeCaballosBBDD {

    private static final String URL = "jdbc:mysql://localhost:3306/carreradecaballos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private static int nuevoIdPartida = 1;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Conectarse al servidor MySQL sin especificar una base de datos
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                // Ahora seleccionar explícitamente la base de datos 'carreradecaballos'
                Statement stmt = connection.createStatement();
                stmt.execute("USE carreradecaballos");
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

    public static int crearTablas() {


        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Crear base de datos y seleccionar esquema
            crearBaseDeDatos();
            stmt.execute("USE carreradecaballos;");

            // Crear la tabla 'partidas' si no existe
            String createTablePartidas = """
        CREATE TABLE IF NOT EXISTS partidas (
            idPartida INT AUTO_INCREMENT PRIMARY KEY
        );
        """;
            stmt.execute(createTablePartidas);

            // Obtener el nuevo idPartida
            var rs = stmt.executeQuery("SELECT MAX(idPartida) FROM partidas");
            if (rs.next()) {
                nuevoIdPartida = rs.getInt(1) + 1;
            }

            // Insertar la nueva partida
            String insertarPartida = "INSERT INTO partidas (idPartida) VALUES (" + nuevoIdPartida + ")";
            stmt.execute(insertarPartida);

            // Crear tablas dinámicas jugadores<N>
            String createTableJugadores = """
        CREATE TABLE IF NOT EXISTS jugadores%s (
            idJugador INT AUTO_INCREMENT PRIMARY KEY,
            nombre VARCHAR(255) NOT NULL,
            palo VARCHAR(50) NOT NULL,
            bote INT NOT NULL,
            posicion INT,
            idPartida INT,
            FOREIGN KEY (idPartida) REFERENCES partidas(idPartida)
        );
        """.formatted(nuevoIdPartida);

            String createTableRondas = """
CREATE TABLE IF NOT EXISTS rondas%s (
    numRonda INT NOT NULL,
    numCarta VARCHAR(5) NOT NULL,
    paloCarta VARCHAR(50) NOT NULL,
    PRIMARY KEY (numRonda)  -- numRonda es auto-incremental y clave primaria
);
""".formatted(nuevoIdPartida);

           /* String createTableRondas = """
CREATE TABLE IF NOT EXISTS rondas%s (
    idPartida INT NOT NULL,
    numRonda INT NOT NULL AUTO_INCREMENT,
    numCarta INT NOT NULL,
    paloCarta VARCHAR(50) NOT NULL,
    PRIMARY KEY (numRonda),  -- numRonda es auto-incremental y clave primaria
    FOREIGN KEY (idPartida) REFERENCES partidas(idPartida)
);
""".formatted(nuevoIdPartida);*/

            ejecutarScript(createTableJugadores);
            ejecutarScript(createTableRondas);

        } catch (SQLException e) {
            System.out.println("Error al crear tablas: " + e.getMessage());
        }
        return nuevoIdPartida;
    }


    public static List<Integer> cargarPartidasDesdeDB() {
        List<Integer> partidas = new ArrayList<>();
        String query = "SELECT idPartida FROM partidas";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                partidas.add(rs.getInt("idPartida"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidas;
    }




    public static void guardarRonda(int ronda, String valor, String palo){

        String sql = "INSERT INTO rondas" + nuevoIdPartida +" (numRonda, numCarta, paloCarta) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ronda);
            stmt.setString(2, valor);
            stmt.setString(3, palo);
            stmt.executeUpdate();

            System.out.println("Carta guardada correctamente:" + ronda + valor + " de " + palo);

        } catch (SQLException e) {
            System.out.println("Error al guardar la carta en la base de datos: " + e.getMessage());
        }
    }




    public static void main(String[] args) {
        crearTablas();
    }
}