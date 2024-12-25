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
    // Método para manejar el clic en "Recuperar partida" y cambiar a la pantalla de recuperación
    protected void onRecuperarButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/partida.fxml"  // Aquí la ruta al archivo de recuperación
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
}
