package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Datenbank;
import com.example.zeiterfassunggui.classes.Worker;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;

public class ZeiterfassungController {
    Datenbank db = ZeiterfassungApplication.getdb();
    Worker activeWorker = null;
    @FXML
    private TextField persNum;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label gesamtZeit;
    @FXML
    private Label anfangsZeit;
    @FXML
    private Label endZeit;
    @FXML
    private Button anfangButton;
    @FXML
    private Button endeButton;
    @FXML
    private Button logoutButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label hoursMonth;

    @FXML
    private Button registerButton;





    public ZeiterfassungController() throws SQLException {
    }


    @FXML
    public void initialize() throws SQLException {
        anfangButton.setDisable(true);
        endeButton.setDisable(true);
        logoutButton.setDisable(true);
        loginButton.setOnMouseEntered(me -> loginButton.setStyle("-fx-background-color: lightgreen; -fx-border-color: green; -fx-border-radius: 5px"));
        loginButton.setOnMouseExited(me -> loginButton.setStyle(""));
        persNum.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickLogin();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
 /*
        db.DELETEALLYOUSERFROMTABLER("User");
        db.SETNEWUSER(4711, "Max", "Mustermann", 0);
        db.SETNEWUSER(4712, "John", "Doe", 0);
        db.SETNEWUSER(4713, "Martina", "Musterfrau", 0);
        System.out.println("Users:");
        db.SHOWALLUSER();
*/
        db.SHOWALLUSER();
    }

    @FXML
    protected void onClickRegister() throws IOException {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ZeiterfassungApplication.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    protected void onClickLogin() throws SQLException {

        try {
            int tempPersNum = Integer.parseInt(persNum.getText());
            welcomeLabel.setText("Personalnummer nicht bekannt!");
            if (db.getUser(tempPersNum) != null){
                activeWorker = db.getUser(tempPersNum);
                    welcomeLabel.setText("Hallo "+activeWorker.getFirstName()+" "+activeWorker.getLastName());
                    if (Worker.acticeWorker.contains(activeWorker)){
                        loginButton.setDisable(true);
                        logoutButton.setDisable(false);
                        endeButton.setDisable(false);
                        hoursMonth.setText(db.getMonthHouer(activeWorker));
                        anfangsZeit.setText(activeWorker.getAnfangszeit());
                        persNum.setDisable(true);
                        registerButton.setDisable(true);
                    } else {
                        registerButton.setDisable(true);
                        logoutButton.setDisable(false);
                        loginButton.setDisable(true);
                        persNum.setDisable(true);
                        hoursMonth.setText(db.getMonthHouer(activeWorker));
                        anfangButton.setDisable(false);
                    }
            };
        } catch (Exception e){
            welcomeLabel.setText("Keine Gültige Zahl!");
        }finally {

            persNum.setText("");
        }
    }

    @FXML
    protected void startDay(){

        anfangsZeit.setText(db.startDay(activeWorker));
        endeButton.setDisable(false);
        anfangButton.setDisable(true);
        endZeit.setText("");
    }

    @FXML
    protected void endDay(){
        endZeit.setText(db.stopDay(activeWorker));
        gesamtZeit.setText(db.getTimeDifference(activeWorker));
        hoursMonth.setText(db.getMonthHouer(activeWorker));
        endeButton.setDisable(true);
        anfangButton.setDisable(false);
    }

    @FXML
    protected void logout(){
        persNum.setDisable(false);
        hoursMonth.setText("");
        activeWorker = null;
        welcomeLabel.setText("");
        anfangsZeit.setText("");
        endZeit.setText("");
        gesamtZeit.setText("");
        loginButton.setDisable(false);
        logoutButton.setDisable(true);
        anfangButton.setDisable(true);
        endeButton.setDisable(true);
        registerButton.setDisable(false);
    }



}