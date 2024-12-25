package org.example.carreradecaballosm03uf5.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PartidaController {

    @FXML
    private Label textoPartida;

    // Método que se ejecutará cuando el usuario haga clic en el botón "Continuar"
    @FXML
    private void onContinuarButtonClick(ActionEvent actionEvent) {
        try {
            // Cargar pantalla del tablero
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/tablero.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            TableroController controller = fxmlLoader.getController();

            //controller.setJugadores(jugadores);

            controller.iniciarTablero(new Stage());
            // Obtener el Stage actual y cerrarlo después de abrir la nueva pantalla
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Asegúrate de que el método cargarPartida exista en esta clase
    public void cargarPartida(int partidaId) {
        // Lógica para cargar la partida usando el partidaId
        System.out.println("Cargando la partida con ID: " + partidaId);
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