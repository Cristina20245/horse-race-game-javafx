package org.example.carreradecaballosm03uf5.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.carreradecaballosm03uf5.bbdd.CarreraDeCaballosBBDD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.example.carreradecaballosm03uf5.bbdd.CarreraDeCaballosBBDD.nuevoIdPartida;

public class PartidaController {

    @FXML
    private Label textoPartida;

    @FXML
    private ListView<Integer> listaPartidas;

    @FXML
    private Button botonContinuar;

    @FXML
    private Button botonSalir;

    private int partidaSeleccionada;

    @FXML
    private Button salirButton;


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
                cargarPartida(partidaSeleccionada);

                // Llamar a recuperar la posición de los jugadores
                List<Integer> idsJugadores = CarreraDeCaballosBBDD.obtenerIdsJugadoresDePartida(partidaSeleccionada);
                for (int idJugador : idsJugadores) {
                    recuperarPosicionCaballo(idJugador, nuevoIdPartida); // Recuperar la posición de cada jugador
                }

                // Cargar pantalla del tablero
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/tablero.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                TableroController controller = fxmlLoader.getController();
                controller.iniciarTablero(new Stage());

                // Obtener el Stage actual y cerrarlo después de abrir la nueva pantalla
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();
            } else {
                System.out.println("No se ha seleccionado ninguna partida.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Método para obtener los IDs de los jugadores en la partida
    public List<Integer> obtenerIdsJugadores(int idPartida) {
        // Aquí puedes añadir la lógica para obtener los jugadores de la base de datos
        // Devuelve una lista con los IDs de los jugadores en la partida
        return CarreraDeCaballosBBDD.obtenerIdsJugadoresDePartida(idPartida);
    }

    // Lógica para cargar la partida usando el partidaId
    public void cargarPartida(int idPartida) {
        System.out.println("Cargando la partida con ID: " + idPartida);
        // Aquí puedes añadir la lógica para cargar datos desde la base de datos
    }


    // Método para el botón "Salir"
    @FXML
    public void onSalirButtonClick() {
        Platform.exit();
        System.exit(0); // Cierra la aplicación
    }

    private void recuperarPosicionCaballo(int idJugador, int idPartida) {
        String sql = "SELECT posicion FROM jugadores" + idPartida + " WHERE idJugador = ?";

        try (Connection conn = CarreraDeCaballosBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idJugador);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String posicion = rs.getString("posicion");
                if (posicion != null && !posicion.isEmpty()) {
                    String[] coordenadas = posicion.split(",");
                    int linha = Integer.parseInt(coordenadas[0]);
                    int coluna = Integer.parseInt(coordenadas[1]);
                    // Llamar a un método para reposicionar el caballo en el tablero
                    reposicionarCaballo(coluna, linha);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al recuperar la posición del caballo: " + e.getMessage());
        }
    }




    private void reposicionarCaballo(int coluna, int linha) {
    }


}
