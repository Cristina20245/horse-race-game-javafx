package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarreraDeCaballosBBDD {

    private static final String URL = "jdbc:mysql://localhost:3306/carreradecaballos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static int nuevoIdPartida = 1;

    // Método para obtener o establecer la conexión a la base de datos
    public static Connection getConnection() {
        if (connection == null) {
            try {
                crearBaseDeDatos();
                connection = DriverManager.getConnection(URL , USER, PASSWORD);
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

    // Crear tablas y retornar el nuevo ID de partida
    public static int crearTablas() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Crear la tabla 'partidas' si no existe
            String createTablePartidas = """
                CREATE TABLE IF NOT EXISTS partidas (
                    idPartida INT AUTO_INCREMENT PRIMARY KEY,
                    estado VARCHAR(10) NOT NULL DEFAULT 'pendiente' -- Estado inicial por defecto
                );
            """;
            stmt.execute(createTablePartidas);

            // Obtener el nuevo idPartida
            ResultSet rs = stmt.executeQuery("SELECT MAX(idPartida) AS maxId FROM partidas");
            if (rs.next()) {
                nuevoIdPartida = rs.getInt("maxId") + 1;
            }

            // Insertar la nueva partida
            String insertarPartida = "INSERT INTO partidas (estado) VALUES ('pendiente')";
            stmt.execute(insertarPartida);

            // Crear tabla dinámica `jugadores<N>`
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
                    PRIMARY KEY (numRonda)
                );
            """.formatted(nuevoIdPartida);

            ejecutarScript(createTableJugadores);
            ejecutarScript(createTableRondas);

        } catch (SQLException e) {
            System.out.println("Error al crear tablas: " + e.getMessage());
        }
        return nuevoIdPartida;
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


    // Método para obtener partidas guardadas
    public static List<Integer> obtenerPartidasGuardadas() {
        List<Integer> partidas = new ArrayList<>();
        String query = "SELECT idPartida FROM partidas";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                partidas.add(rs.getInt("idPartida"));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener partidas guardadas: " + e.getMessage());
        }

        return partidas;
    }
    public static List<Integer> obtenerIdsJugadoresDePartida(int idPartida) {
        List<Integer> idsJugadores = new ArrayList<>();
        // Usar el idPartida para construir el nombre de la tabla dinámica
        String tableName = "jugadores" + idPartida;
        String sql = "SELECT idJugador FROM " + tableName; // Concatenar directamente el nombre de la tabla

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Ejecutar la consulta y obtener el ResultSet
            try (ResultSet rs = stmt.executeQuery()) {
                // Iterar sobre los resultados y agregar los idJugador a la lista
                while (rs.next()) {
                    idsJugadores.add(rs.getInt("idJugador"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los IDs de los jugadores: " + e.getMessage());
        }

        return idsJugadores;
    }

    // Método para guardar las posiciones de los caballos en la base de datos antes de salir
    public void guardarPosicionesAntesDeSalir(int idPartida, Map<String, Integer> posicionesCaballos) {
        String tablaJugadores = "jugadores" + idPartida;

        // Crear la consulta SQL para actualizar las posiciones de los caballos
        String sql = "UPDATE " + tablaJugadores + " SET posicion = ? WHERE palo = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Iterar por los palos y sus posiciones
            for (Map.Entry<String, Integer> entry : posicionesCaballos.entrySet()) {
                String palo = entry.getKey();
                int posicion = entry.getValue();

                // Configurar los parámetros de la consulta
                stmt.setInt(1, posicion); // Posición del caballo
                stmt.setString(2, palo); // Palo del caballo
                stmt.executeUpdate();    // Ejecutar la consulta
            }

            System.out.println("Posiciones guardadas correctamente en la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al guardar las posiciones en la base de datos: " + e.getMessage());
        }
    }







    public static void main(String[] args) {
        int idPartida = crearTablas();
        System.out.println("Nueva partida creada con ID: " + idPartida);

        List<Integer> partidas = obtenerPartidasGuardadas();
        System.out.println("Partidas guardadas: " + partidas);
    }
}
