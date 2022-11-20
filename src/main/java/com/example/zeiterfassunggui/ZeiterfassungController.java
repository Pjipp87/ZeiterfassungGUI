package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Datenbank;
import com.example.zeiterfassunggui.classes.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    public ZeiterfassungController() throws SQLException {
    }


    @FXML
    public void initialize() throws SQLException {
        anfangButton.setDisable(true);
        endeButton.setDisable(true);
        logoutButton.setDisable(true);

/*        db.DELETEALLYOUSERFROMTABLER("User");
        db.SETNEWUSER(4711, "Max", "Mustermann", 0);
        db.SETNEWUSER(4712, "John", "Doe", 0);
        db.SETNEWUSER(4713, "Martina", "Musterfrau", 0);
        System.out.println("Users:");
        db.SHOWALLUSER();*/
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
                        anfangsZeit.setText(activeWorker.getAnfangszeit());
                    } else {
                        logoutButton.setDisable(false);
                        loginButton.setDisable(true);
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
        ;
        //Database integration
        endZeit.setText(db.stopDay(activeWorker));
        gesamtZeit.setText(activeWorker.getHours());
        endeButton.setDisable(true);
        anfangButton.setDisable(false);
    }

    @FXML
    protected void logout(){
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

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }


}