package org.example.carreradecaballosm03uf5.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.application.Platform;
import org.example.carreradecaballosm03uf5.bbdd.CarreraDeCaballosBBDD;

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

    public void onRecuperarButtonClick() {
        // Llamar a obtener partidas
        if (!CarreraDeCaballosBBDD.obtenerPartidasGuardadas().isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/partida.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) textoBienvenida.getScene().getWindow();  // Obtener el Stage actual
                Scene scene = new Scene(root);

                root.sceneProperty().addListener((obs, oldScene, newScene) -> {
                    if (newScene != null) {
                        root.requestLayout();
                    }
                });

                stage.setScene(scene);  // Cambiar la escena de la ventana actual
                stage.setMaximized(false);
                stage.setMaximized(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay partidas guardadas.");
        }
    }


    @FXML
    // Método para manejar el clic en "Salir"
    protected void onSalirButtonClick() {
        Platform.exit();
        System.exit(0);
    }
}