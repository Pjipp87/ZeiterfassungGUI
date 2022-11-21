package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Datenbank;
import com.example.zeiterfassunggui.classes.Worker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class ZeiterfassungApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ZeiterfassungApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Zeiterfassung");
        stage.setScene(scene);
        stage.show();

    }

    public static Datenbank getdb(){
        return new Datenbank();
    }

    public static void main(String[] args) throws SQLException {
        launch();

    }
}