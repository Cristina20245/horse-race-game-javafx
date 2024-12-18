module org.example.carreradecaballosm03uf5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.carreradecaballosm03uf5 to javafx.fxml;
    exports org.example.carreradecaballosm03uf5;
    exports org.example.carreradecaballosm03uf5.controllers;
    exports org.example.carreradecaballosm03uf5.model;
    opens org.example.carreradecaballosm03uf5.controllers to javafx.fxml;
}