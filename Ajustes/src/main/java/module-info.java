module com.example.ajustes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ajustes to javafx.fxml;
    exports com.example.ajustes;
}