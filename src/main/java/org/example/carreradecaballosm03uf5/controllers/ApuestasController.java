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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.carreradecaballosm03uf5.bbdd.ConexionDB;
import org.example.carreradecaballosm03uf5.model.Jugador;
import org.example.carreradecaballosm03uf5.bbdd.CarreraDeCaballosBBDD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApuestasController {

    private Jugador[] jugadores;  // Variable jugadores
    private int idPartida;  // Variable idPartida

    @FXML
    private GridPane jugadoresGrid; // Contenedor de los jugadores

    // Establecer el valor de jugadores
    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }

    // Establecer el valor de idPartida
    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    // Llamada al guardar apuestas cuando es necesario
    public void procesarApuestas(Jugador[] jugadores, int idPartida) {
        // Llamamos al método guardarApuestas que ya tienes
        guardarApuestas(jugadores, idPartida);

        // Aquí podrías agregar más lógica, como actualizar la UI, etc.
        System.out.println("Apuestas guardadas correctamente.");
    }
    public static List<Jugador> guardarApuestas(Jugador[] jugadores, int idPartida) {
        List<Jugador> jugadoresGuardados = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement()) {

            for (Jugador jugador : jugadores) {
                String insertJugador = """
            INSERT INTO jugadores%s (nombre, palo, bote, posicion, idPartida)
            VALUES ('%s', '%s', %d, %d, %d);
            """.formatted(
                        idPartida, // Tabla dinámica
                        jugador.getNombre(),
                        jugador.getPalo().getDescription(),
                        jugador.getFichas(),
                        0, // Posición inicial
                        idPartida // idPartida asociado
                );
                stmt.executeUpdate(insertJugador);
                jugadoresGuardados.add(jugador); // Agregar jugador a la lista
            }

            System.out.println("Apuestas guardadas en la base de datos para la partida " + idPartida);

        } catch (SQLException e) {
            System.out.println("Error al guardar las apuestas: " + e.getMessage());
        }
        return jugadoresGuardados; // Devolver lista de jugadores
    }



    // Mostrar las apuestas, como ya tienes implementado
    public void mostrarApuestasDesdeLogica(Jugador[] jugadores) {
        try {
            setJugadores(jugadores);  // Asignar los jugadores a la variable
            for (int i = 0; i < jugadores.length; i++) {
                Jugador jugador = jugadores[i];

                VBox jugadorVBox = new VBox(10);
                jugadorVBox.setAlignment(Pos.CENTER);
                jugadorVBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-color: white;");
                jugadorVBox.setPrefWidth(150);
                jugadorVBox.setPrefHeight(150);

                ImageView cartaImageView = new ImageView();
                // Ruta de la imagen del caballo
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
// Método para continuar a la siguiente pantalla
    public void onContinuarButtonClick(ActionEvent actionEvent) {
        try {
            // Crear las tablas y asignar idPartida
            idPartida = CarreraDeCaballosBBDD.crearTablas(); // Este método devolverá el nuevo idPartida
            System.out.println("Tablas creadas con éxito para la partida: " + idPartida);

            // Guardar las apuestas en la base de datos
            guardarApuestas(jugadores, idPartida);

            // Cargar pantalla del tablero
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/tablero.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            TableroController controller = fxmlLoader.getController();
            controller.setJugadores(jugadores);

            // Inicializar el tablero con los datos de la partida
            // Cambiar la escena sin cerrar la ventana actual
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);

            // Maximizar la ventana (opcional)
            stage.setMaximized(false);  // Primero desactivar la maximización
            stage.setMaximized(true);   // Luego activar la maximización

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    // Método para volver a la pantalla de configuración
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