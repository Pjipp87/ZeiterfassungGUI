package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Worker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ZeiterfassungApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ZeiterfassungApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Zeiterfassung");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Worker john = new Worker("John", "Doe", 4711);
        launch();
    }
}