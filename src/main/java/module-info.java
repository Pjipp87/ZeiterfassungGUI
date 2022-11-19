module com.example.zeiterfassunggui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zeiterfassunggui to javafx.fxml;
    exports com.example.zeiterfassunggui;
}