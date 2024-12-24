package org.example.carreradecaballosm03uf5.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.carreradecaballosm03uf5.model.CardSuit;
import org.example.carreradecaballosm03uf5.model.Jugador;

import java.io.IOException;
import java.io.InputStream;


public class ResultadoController {
    @FXML
    private Label ganadorLabel;
    @FXML
    private ImageView caballoImageView;
    @FXML
    private Label boteLabel;

    //Método para mostrar quien ganó la partida
    public void mostrarGanador(Jugador ganador, Integer bote) {

        ganadorLabel.setText("El ganador es: " + ganador.getNombre()+ " con el palo "+ ganador.getPalo().getDescription());


        CardSuit suit = ganador.getPalo();


        String caminoImagen = "/images/KNIGHT_" + suit + ".png";
        InputStream imagenStream = getClass().getResourceAsStream(caminoImagen);

        if (imagenStream != null) {

            Image imagenCaballo = new Image(imagenStream);
            caballoImageView.setImage(imagenCaballo);
            caballoImageView.setFitHeight(120);
            caballoImageView.setPreserveRatio(true);
        }


        boteLabel.setText("Bote final: " + bote);
    }

    @FXML
    // Método para volver a la pantalla de apuestas
    public void onVolverButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/configuracion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.setTitle("Carrera de Caballos - Apuestas");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    //Método para salir de la aplicación
    private void onSalirButtonClick() {
        Platform.exit();
        System.exit(0);
    }
}