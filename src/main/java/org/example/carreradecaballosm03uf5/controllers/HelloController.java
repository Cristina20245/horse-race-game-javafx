package org.example.carreradecaballosm03uf5.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.application.Platform;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label textoBienvenida;

    @FXML
    // Método para manejar el clic en "Jugar" y cambiar a la pantalla de configuración
    protected void onJugarButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/org/example/carreradecaballosm03uf5/views/configuracion.fxml"
        ));

        Parent root = fxmlLoader.load();
        Stage stage = (Stage) textoBienvenida.getScene().getWindow();
        Scene scene = new Scene(root);

        root.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                root.requestLayout();
            }
        });

        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setMaximized(true);
    }

    @FXML
    // Método para manejar el clic en "Salir"
    protected void onSalirButtonClick() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
// Método para manejar el clic en "Recuperar Partida"
    protected void onRecuperarButtonClick() throws IOException {
        // Recuperar el último idPartida desde la base de datos
        int idPartidaRecuperada = recuperarUltimaPartida();

        // Cargar la pantalla de la partida recuperada
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/org/example/carreradecaballosm03uf5/views/partida.fxml"
        ));

        // Cargar la vista
        Parent root = fxmlLoader.load();

        // Obtener el controlador de la vista cargada
        PartidaController partidaController = fxmlLoader.getController();

        // Pasar el idPartidaRecuperada al controlador de la nueva vista
        partidaController.cargarPartida(idPartidaRecuperada);

        // Configurar la escena
        Stage stage = (Stage) textoBienvenida.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setMaximized(true);
    }


    // Método para recuperar la última partida desde la base de datos
    private int recuperarUltimaPartida() {
        // Lógica para obtener el idPartida desde la base de datos
        // Aquí utilizamos un valor ficticio
        int idPartida = 1; // Deberías hacer una consulta a la base de datos para obtener el idPartida real
        return idPartida;
    }
}
