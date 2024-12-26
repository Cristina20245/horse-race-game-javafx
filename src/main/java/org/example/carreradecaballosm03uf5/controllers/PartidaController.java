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
import java.util.List;

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

    // Método que se ejecutará cuando el usuario haga clic en el botón "Continuar"
    @FXML
    private void onContinuarButtonClick(ActionEvent actionEvent) {
        try {
            if (partidaSeleccionada > 0) {
                System.out.println("Partida seleccionada: " + partidaSeleccionada);
                cargarPartida(partidaSeleccionada);

                // Cargar pantalla del tablero
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/tablero.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                TableroController controller = fxmlLoader.getController();
                // Configurar datos para la pantalla del tablero si es necesario
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

    // Lógica para cargar la partida usando el partidaId
    public void cargarPartida(int partidaId) {
        System.out.println("Cargando la partida con ID: " + partidaId);
        // Aquí puedes añadir la lógica para cargar datos desde la base de datos
    }

    // Método para el botón "Salir"
    @FXML
    public void onSalirButtonClick() {
        Platform.exit();
        System.exit(0); // Cierra la aplicación
    }

    // Método para manejar el cambio de pantalla, similar a lo que hiciste en ConfiguracionController
    public void cargarSiguientePantalla(ActionEvent actionEvent) {
        try {
            // Cargar la siguiente pantalla
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/nextScene.fxml"));
            Parent root = loader.load();

            // Configuración de la escena
            Scene scene = new Scene(root);
            Stage stage = (Stage) textoPartida.getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(true); // Maximizar la ventana
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
