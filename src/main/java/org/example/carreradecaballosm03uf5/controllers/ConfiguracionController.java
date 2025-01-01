package org.example.carreradecaballosm03uf5.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import org.example.carreradecaballosm03uf5.model.CardSuit;
import org.example.carreradecaballosm03uf5.model.Jugador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.example.carreradecaballosm03uf5.Utils.CreadorDeJugadores.crearJugadores;

public class ConfiguracionController {

    @FXML
    private TextField playerNameField; // Campo para el nombre del jugador
    @FXML
    private TextField betField;       // Campo para las fichas a apostar
    @FXML
    private ComboBox<String> paloComboBoxField; // Campo ComboBox para el palo
    @FXML
    private Label feedbackLabel;      // Etiqueta para mostrar mensajes




    // Método para manejar el clic en el botón "Apostar"
    public void onApostarButtonClick(ActionEvent actionEvent) {
        // Obtener los valores de los campos
        String playerName = playerNameField.getText();
        String betAmount = betField.getText();
        String paloComboBox = paloComboBoxField.getValue();

        // Validar entrada del usuario
        String validationMessage = validateInput(playerName, betAmount, paloComboBox);
        if (validationMessage != null) {
            feedbackLabel.setText(validationMessage);
            return;
        }

        // Convertir los valores validados
        int bet = Integer.parseInt(betAmount);
        CardSuit palo = mapPaloToEnum(paloComboBox);

        // Registrar apuesta y cargar jugadores
        feedbackLabel.setText("Apuesta registrada, cargando apuestas...");
        crearJugadores(playerName, palo, bet);

        // Cargar la siguiente pantalla
        cargarSiguientePantalla(actionEvent, playerName, bet, palo);
    }

    // Validación de campos vacíos
    private String validateInput(String playerName, String betAmount, String paloComboBox) {

        if (playerName == null || playerName.isEmpty() || betAmount == null || betAmount.isEmpty() || paloComboBox == null) {
            return "Por favor, completa todos los campos.";
        }

        // Validación del nombre del jugador
        if (!playerName.matches("[a-zA-Z]+")) {
            return "El nombre del jugador solo puede contener letras.";
        }

        // Validación de la apuesta
        try {
            int bet = Integer.parseInt(betAmount);
            if (bet < 1 || bet > 100) {
                return "La apuesta debe estar entre 1 y 100 fichas.";
            }
        } catch (NumberFormatException e) {
            return "La apuesta debe ser un número válido.";
        }

        return null; // Todo es válido
    }

    // Mapear el palo
    private CardSuit mapPaloToEnum(String paloComboBox) {
        Map<String, CardSuit> paloMap = new HashMap<>();
        paloMap.put("Oros", CardSuit.GOLD);
        paloMap.put("Espadas", CardSuit.SWORDS);
        paloMap.put("Copas", CardSuit.CUPS);
        paloMap.put("Bastos", CardSuit.CLUBS);

        return paloMap.getOrDefault(paloComboBox, null); // Manejo de valores inesperados
    }

    //Pasar a la pantalla de resumen
    private void cargarSiguientePantalla(ActionEvent actionEvent, String playerName, int bet, CardSuit palo) {
        try {
            // Cargar la pantalla de apuestas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/apuestas.fxml"));

            Parent root = loader.load();

            // Pasar datos al controlador
            ApuestasController apuestasController = loader.getController();

            Jugador[] jugadores = crearJugadores(playerName, palo, bet);

            // Mostrar las apuestas
            apuestasController.mostrarApuestasDesdeLogica(jugadores);

            Scene scene = new Scene(root);
            // Mostrar la nueva pantalla
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            root.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    root.requestLayout();
                }
            });

            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.setTitle("Apuestas Realizadas");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            feedbackLabel.setText("Error al cargar la pantalla de apuestas.");
        }
    }


    @FXML
    // Método para el botón "Salir"
    public void onSalirButtonClick() {
        Platform.exit();
        System.exit(0); // Cierra la aplicación
    }


    @FXML
    // Método para manejar el clic en el botón "Volver"
    public void onVolverButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.setTitle("Carrera de Caballos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}