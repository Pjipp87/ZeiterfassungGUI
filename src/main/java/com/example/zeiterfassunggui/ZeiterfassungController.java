package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Datenbank;
import com.example.zeiterfassunggui.classes.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;

public class ZeiterfassungController {

    Datenbank db = new Datenbank();


    Worker john = new Worker("John", "Doe", 4711);
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



    public ZeiterfassungController() throws SQLException {
    }


    @FXML
    public void initialize() throws SQLException {
        anfangButton.setDisable(true);
        endeButton.setDisable(true);
        logoutButton.setDisable(true);

        persNum.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER){
                    try {
                        onClickLogin();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
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
                    } else {
                        logoutButton.setDisable(false);
                        loginButton.setDisable(true);
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
    }



}