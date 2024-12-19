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
import org.example.carreradecaballosm03uf5.bbdd.CarreraDeCaballosBBDD;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/carreradecaballosm03uf5/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Carrera de Caballos");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

        // Crear una instancia de CarreraDeCaballosBBDD y conectar a la base de datos
        CarreraDeCaballosBBDD db = new CarreraDeCaballosBBDD();
        db.conectarYEjecutar();  // Llamamos al método para conectar en un hilo separado
    }

    public static void main(String[] args) {
        launch();
    }
}
