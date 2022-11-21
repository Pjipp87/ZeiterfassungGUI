package com.example.zeiterfassunggui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrierungControler {
    @FXML
    private Button goBackButton;


    @FXML
    protected void goBack() throws IOException {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ZeiterfassungApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Zeiterfassung");
        stage.setScene(scene);
        stage.show();
    }
}
