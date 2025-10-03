module com.example.serie {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.serie to javafx.fxml;
    exports com.example.serie;
}