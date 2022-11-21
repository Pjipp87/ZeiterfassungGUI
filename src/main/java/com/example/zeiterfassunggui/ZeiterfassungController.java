package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Datenbank;
import com.example.zeiterfassunggui.classes.Worker;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @FXML
    private Label uhr;






    @FXML
    public void initialize() throws SQLException, InterruptedException {
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


        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, kf ->
                uhr.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        db.getAllUserFromDB();



 /*
        db.DELETEALLYOUSERFROMTABLER("User");
        db.SETNEWUSER(4711, "Max", "Mustermann", 0);
        db.SETNEWUSER(4712, "John", "Doe", 0);
        db.SETNEWUSER(4713, "Martina", "Musterfrau", 0);
        System.out.println("Users:");

*/

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
                    if (activeWorker.getArbeitet() == 1){
                        loginButton.setDisable(true);
                        logoutButton.setDisable(false);
                        endeButton.setDisable(false);
                        hoursMonth.setText(db.getMonthHouer(activeWorker));
                        anfangsZeit.setText(db.getAnfangszeitFromDB(activeWorker));
                        //anfangsZeit.setText(activeWorker.getAnfangszeit());
                        //anfangsZeit.setText("Test");
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
            System.err.println(e);
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
        db.setActive(activeWorker,1);
        activeWorker.setArbeitet(1);
        endZeit.setText("");
    }

    @FXML
    protected void endDay(){
        endZeit.setText(db.stopDay(activeWorker));
        gesamtZeit.setText(db.getTimeDifference(activeWorker));
        hoursMonth.setText(db.getMonthHouer(activeWorker));
        db.setActive(activeWorker,0);
        activeWorker.setArbeitet(0);
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