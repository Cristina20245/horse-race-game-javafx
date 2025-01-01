package org.example.carreradecaballosm03uf5.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.carreradecaballosm03uf5.bbdd.CarreraDeCaballosBBDD;
import org.example.carreradecaballosm03uf5.bbdd.ConexionDB;
import org.example.carreradecaballosm03uf5.model.Jugador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.carreradecaballosm03uf5.Utils.CreadorDeJugadores.crearJugadores;

public class PartidaController {

    @FXML
    private ListView<Integer> listaPartidas;

    @FXML
    private Button botonContinuar;

    private int partidaSeleccionada;

    @FXML
    public void initialize() {
        // Cargar partidas desde la base de datos
        List<Integer> partidas = CarreraDeCaballosBBDD.obtenerPartidasGuardadas();
        listaPartidas.setItems(FXCollections.observableArrayList(partidas));

        // Manejar selección de partida
        listaPartidas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            partidaSeleccionada = newValue != null ? newValue : 0;
            botonContinuar.setDisable(partidaSeleccionada == 0); // Deshabilita si no hay selección
        });
    }

    @FXML
    private void onContinuarButtonClick(ActionEvent actionEvent) {
        try {
            if (partidaSeleccionada > 0) {
                System.out.println("Partida seleccionada: " + partidaSeleccionada);

                // Llamar para recuperar la posición de los caballos
                Map<String, Integer> posicicionDeLosCaballos = recuperarPosicionCaballo(partidaSeleccionada);

                // Carga los jugadores de la partida
                List<Jugador> jugadoresList = CarreraDeCaballosBBDD.getPlayers(partidaSeleccionada);

                // Cargar pantalla del tablero
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/tablero.fxml"));
                new Scene(fxmlLoader.load());

                TableroController controller = fxmlLoader.getController();
                controller.setJugadores(jugadoresList.toArray(Jugador[]::new));
                controller.iniciarTablero(new Stage(), jugadoresList, partidaSeleccionada, posicicionDeLosCaballos, true);

                // Obtener el Stage actual y cerrarlo después de abrir la nueva pantalla
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();
            } else {
                System.out.println("No se ha seleccionado ninguna partida.");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Método para el botón "Salir"
    @FXML
    public void onSalirButtonClick() {
        Platform.exit();
        System.exit(0); // Cierra la aplicación
    }

    private Map<String, Integer> recuperarPosicionCaballo(int idPartida) {
        Map<String, Integer> posicicionDeLosCaballos = new HashMap<>();

        String sql = "SELECT palo, columna FROM jugadores WHERE idPartida = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPartida);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String palo = rs.getString("palo");
                int columna = rs.getInt("columna");

                //actualiza Map con las posiciones de los caballos
                posicicionDeLosCaballos.put(palo, columna);
            }
        } catch (SQLException e) {
            System.err.println("Error al recuperar la posición del caballo: " + e.getMessage());
        }
        return posicicionDeLosCaballos;
    }

}
