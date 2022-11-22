module com.example.zeiterfassunggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;


    opens com.example.zeiterfassunggui to javafx.fxml;
    exports com.example.zeiterfassunggui;
    opens com.example.zeiterfassunggui.classes to javafx.base;
}