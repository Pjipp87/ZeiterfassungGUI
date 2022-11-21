package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Datenbank;
import com.example.zeiterfassunggui.classes.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RegistrierungControler {
    Datenbank db = ZeiterfassungApplication.getdb();
    @FXML
    private Button goBackButton;

    @FXML
    private TextField number;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;

    @FXML
    private Label warning;



    @FXML
    public void initialize() {
        number.setText("");
        firstname.setText("");
        lastname.setText("");
    };


    @FXML
    protected void goBack() throws IOException {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ZeiterfassungApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Zeiterfassung");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void saveNewUser() throws SQLException, IOException {
        if (!Objects.equals(number.getText(), "") && firstname.getText()!= "" && lastname.getText() != ""){
            if (!Worker.workerListFromDB.contains(Integer.valueOf(number.getText()))){
                db.SETNEWUSER(Integer.parseInt(number.getText()), firstname.getText(), lastname.getText(), 0);
                goBack();
            } else{
                warning.setText("Personalnummer existiert schon!");
                warning.setVisible(true);
            }
        } else{
            warning.setText("Bitte alle Felder ausfüllen!");
            warning.setVisible(true);
        }

    }
}
