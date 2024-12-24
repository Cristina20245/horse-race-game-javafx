package org.example.carreradecaballosm03uf5.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent; // Importación correcta
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.carreradecaballosm03uf5.bbdd.CarreraDeCaballosBBDD;

import java.io.IOException;
import java.util.List;

public class PartidaController {

    @FXML
    private TableView<Integer> tableViewPartidas;
    @FXML
    private TableColumn<Integer, Integer> colIdPartida;

    @FXML
    public void initialize() {
        cargarPartidas();
    }

    private void cargarPartidas() {
        List<Integer> partidas = CarreraDeCaballosBBDD.cargarPartidasDesdeDB();
        colIdPartida.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        ObservableList<Integer> partidasList = FXCollections.observableArrayList(partidas);
        tableViewPartidas.setItems(partidasList);
    }


    @FXML
    private void onContinuarButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/tablero.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            TableroController controller = fxmlLoader.getController();

            //controller.setJugadores(jugadores);

            controller.iniciarTablero(new Stage());
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSalirButtonClick() {
        Platform.exit();
        System.exit(0);
    }
}
