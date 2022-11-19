module com.example.zeiterfassunggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.zeiterfassunggui to javafx.fxml;
    exports com.example.zeiterfassunggui;
}