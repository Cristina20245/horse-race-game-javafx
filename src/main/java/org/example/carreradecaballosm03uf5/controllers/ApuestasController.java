package org.example.carreradecaballosm03uf5.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.carreradecaballosm03uf5.model.Jugador;

import java.io.IOException;

public class ApuestasController {

    private Jugador[] jugadores;


    @FXML
    private GridPane jugadoresGrid;

    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }

    // Método para mostrar las apuestas en la pantalla




   ///Metodo para mostrar un resumen de los jugadores y sus apuestas
    public void mostrarApuestasDesdeLogica(Jugador[] jugadores) {
        try {

            setJugadores((jugadores));
            for (int i = 0; i < jugadores.length; i++) {
                Jugador jugador = jugadores[i];


                VBox jugadorVBox = new VBox(10);
                jugadorVBox.setAlignment(Pos.CENTER);
                jugadorVBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-color: white;");
                jugadorVBox.setPrefWidth(150);
                jugadorVBox.setPrefHeight(150);

                ImageView cartaImageView = new ImageView();
                String caminoImagen = "/images/KNIGHT_" + jugador.getPalo() + ".png";
                Image caballoImage = new Image(getClass().getResourceAsStream(caminoImagen));
                cartaImageView.setImage(caballoImage);
                cartaImageView.setFitHeight(80);
                cartaImageView.setPreserveRatio(true);

                Label nombreLabel = new Label("Nombre: " + jugador.getNombre());
                nombreLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                Label paloLabel = new Label("Palo: " + jugador.getPalo().getDescription());
                paloLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                Label boteLabel = new Label("Bote: " + jugador.getFichas());
                boteLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");


                jugadorVBox.getChildren().addAll(cartaImageView, nombreLabel, paloLabel, boteLabel);


                jugadoresGrid.add(jugadorVBox, i % 2, i / 2);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    @FXML
    // Metodo para continuar a la siguiente pantalla
    public void onContinuarButtonClick(ActionEvent actionEvent) {
        try {
            // Cargar pantalla del tablero
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/tablero.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            TableroController controller = fxmlLoader.getController();

            controller.setJugadores(jugadores);

            controller.iniciarTablero(new Stage());
            // Obtener el Stage actual y cerrarlo después de abrir la nueva pantalla
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    //Volver a la pantalla de configuración
    public void onVolverButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/hello-view.fxml")); // Pantalla anterior
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.setTitle("Carrera de Caballos - Menú Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
