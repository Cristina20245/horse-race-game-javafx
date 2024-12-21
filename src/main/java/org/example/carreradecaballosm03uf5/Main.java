/*package org.example.carreradecaballosm03uf5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//Subirlo a GitHub

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/carreradecaballosm03uf5/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Carrera de Caballos");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}*/
package org.example.carreradecaballosm03uf5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carreradecaballosm03uf5.bbdd.ConexionDB;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/carreradecaballosm03uf5/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Carrera de Caballos");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

        // Establecer la conexión con la base de datos
        try {
            ConexionDB.getConnection();  // Llamada estática para establecer la conexión
        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción de manera adecuada
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
