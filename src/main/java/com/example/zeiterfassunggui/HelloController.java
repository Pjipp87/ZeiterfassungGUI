package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
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
    public void initialize() {
        anfangButton.setDisable(true);
        endeButton.setDisable(true);
    }


    @FXML
    protected void onClickLogin(){
        try {
            int tempPersNum = Integer.parseInt(persNum.getText());
            welcomeLabel.setText("Personalnummer nicht bekannt!");
            for (Worker w:Worker.workerList) {
                if (w.getId() == tempPersNum){
                    activeWorker = w;
                    welcomeLabel.setText("Hallo "+w.getFirstName()+" "+w.getLastName());
                    if (Worker.acticeWorker.contains(activeWorker)){
                        endeButton.setDisable(false);
                        anfangsZeit.setText(activeWorker.getAnfangszeit());
                    } else {
                        anfangButton.setDisable(false);
                    }
                    break;
                }
            }
        } catch (Exception e){
            welcomeLabel.setText("Keine Gültige Zahl!");
        }finally {
            persNum.setText("");
        }
    }

    @FXML
    protected void startDay(){
        anfangsZeit.setText(activeWorker.setBeginDay());
        endeButton.setDisable(false);
        anfangButton.setDisable(true);
    }

    @FXML
    protected void endDay(){
        endZeit.setText(activeWorker.setEndDay());
        gesamtZeit.setText(activeWorker.getHours());
        endeButton.setDisable(false);
        anfangButton.setDisable(false);
    }

    @FXML
    protected void logout(){
        activeWorker = null;
        welcomeLabel.setText("");
        anfangsZeit.setText("");
        endZeit.setText("");
        gesamtZeit.setText("");
        anfangButton.setDisable(true);
        endeButton.setDisable(true);
    }

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }


}