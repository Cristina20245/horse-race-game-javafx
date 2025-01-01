package org.example.carreradecaballosm03uf5.bbdd;

import org.example.carreradecaballosm03uf5.model.CardSuit;
import org.example.carreradecaballosm03uf5.model.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraDeCaballosBBDD {

    public static int nuevoIdPartida = 1;

    // Crear tablas
    public static void createTables() {
        crearTablaPartidas();
        crearTablaJugadores();
        crearTablaRondas();
    }

    // Crear tabla 'partidas'
    private static void crearTablaPartidas() {
        String createTablePartidas = """
            CREATE TABLE IF NOT EXISTS partidas (
                idPartida INT AUTO_INCREMENT PRIMARY KEY,
                estado VARCHAR(10) NOT NULL DEFAULT 'pendiente'
            );
        """;
        ejecutarScript(createTablePartidas);
    }

    // Crear tabla única para jugadores
    private static void crearTablaJugadores() {
        String createTableJugadores = """
            CREATE TABLE IF NOT EXISTS jugadores (
                idJugador INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(255) NOT NULL,
                palo VARCHAR(50) NOT NULL,
                bote INT NOT NULL,
                linea INT,
                columna INT,
                idPartida INT,
                FOREIGN KEY (idPartida) REFERENCES partidas(idPartida)
            );
        """;
        ejecutarScript(createTableJugadores);
    }

    // Crear tabla única para rondas
    private static void crearTablaRondas() {
        String createTableRondas = """
            CREATE TABLE IF NOT EXISTS rondas (
                numRonda INT NOT NULL,
                numCarta VARCHAR(5) NOT NULL,
                paloCarta VARCHAR(50) NOT NULL,
                idPartida INT,
                FOREIGN KEY (idPartida) REFERENCES partidas(idPartida),
                PRIMARY KEY (numRonda, idPartida)
            );
        """;
        ejecutarScript(createTableRondas);
    }

    // Método genérico para ejecutar scripts SQL
    private static void ejecutarScript(String script) {
        try (Connection conn = ConexionDB.getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(script);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar script SQL: " + e.getMessage());
        }
    }

    // Método para crear la partida
    public static int crearPartida() {
        String insertarPartida = "INSERT INTO partidas (estado) VALUES ('pendiente')";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertarPartida, Statement.RETURN_GENERATED_KEYS)) {

            // Ejecutar la inserción
            stmt.executeUpdate();

            // Obtener el idPartida generado automáticamente
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    nuevoIdPartida = rs.getInt(1); // El primer valor generado
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al crear partida: " + e.getMessage());
        }

        return nuevoIdPartida;
    }

    // Método para guardar ronda
    public static void guardarRonda(int ronda, String valor, String palo, int idPartida) {
        String sql = "INSERT INTO rondas (numRonda, numCarta, paloCarta, idPartida) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ronda);
            stmt.setString(2, valor);
            stmt.setString(3, palo);
            stmt.setInt(4, idPartida);  // Asociamos la ronda con la partida
            stmt.executeUpdate();
            System.out.println("Carta guardada correctamente, ronda: " + ronda + " " + valor + " de " + palo);
        } catch (SQLException e) {
            System.err.println("Error al guardar la carta en la base de datos: " + e.getMessage());
        }
    }

    // Obtener partidas guardadas
    public static List<Integer> obtenerPartidasGuardadas() {
        List<Integer> partidas = new ArrayList<>();
        String query = "SELECT idPartida FROM partidas WHERE estado = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             stmt.setString(1, "pendiente");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                partidas.add(rs.getInt("idPartida"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener partidas guardadas: " + e.getMessage());
        }
        return partidas;
    }

    public static List<Jugador> getPlayers(int idPartida) {
        List<Jugador> jugadoreList = new ArrayList<>();
        String sql = "SELECT idJugador, nombre, palo, bote, linea, columna FROM jugadores WHERE idPartida = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPartida);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Jugador jugador = new Jugador() {
                    @Override
                    public String getDescription() {
                        return null;
                    }
                };

                CardSuit cardSuit = CardSuit.fromDescription(rs.getString("palo"));

                jugador.setIdJugador(rs.getInt("idJugador"));
                jugador.setNombre(rs.getString("nombre"));
                jugador.setPalo(cardSuit);
                jugador.setFichas(rs.getInt("bote"));
                jugador.setLinea(rs.getInt("linea"));
                jugador.setColumna(rs.getInt("columna"));

                jugadoreList.add(jugador);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los jugadores: " + e.getMessage());
        }
        return jugadoreList;
    }

    public static void updatePositions(String card, int linea, int columna, int idPartida) {
        String sql = "UPDATE jugadores SET linea = ?, columna = ? WHERE palo = ? AND idPartida = ?";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, linea);
                stmt.setInt(2, columna);
                stmt.setString(3, card);
                stmt.setInt(4, idPartida);
                stmt.executeUpdate();

            System.out.println("Posiciones guardadas correctamente en la base de datos.");

        } catch (SQLException e) {
            System.err.println("Error al guardar las posiciones en la base de datos: " + e.getMessage());
        }
    }

    public static void updatePartidaState(String state, int idPartida) {
        String sql = "UPDATE partidas SET estado = ? WHERE idPartida = ?";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, state);
            stmt.setInt(2, idPartida);

            stmt.executeUpdate();
            System.out.println("Cambio de estado: " + state + nuevoIdPartida);

        } catch (SQLException e) {
            System.err.println("Error al cambiar el estado en la base de datos: " + e.getMessage());
        }
    }

    // Obtener ultima ronda por patida
    public static int getLastRonda(int idPartida) {
        final String query = "SELECT numRonda FROM rondas WHERE idPartida = ? ORDER BY numRonda DESC LIMIT 1";
        int numRonda = 1; // Valor predeterminado si no se encuentra ninguna ronda

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idPartida); // Establecer el parámetro idPartida
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    numRonda = rs.getInt("numRonda"); // Obtener el número de la última ronda
                }
            }
        } catch (SQLException e) {
            System.err.printf("Error al obtener la última ronda para idPartida %d: %s%n", idPartida, e.getMessage());
        }
        return numRonda;
    }

}
