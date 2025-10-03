module com.example.serie {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.serie to javafx.fxml;
    exports com.example.serie;
}