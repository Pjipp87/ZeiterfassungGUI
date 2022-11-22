package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ListenController {

    @FXML
    private Label title;

    @FXML
    private TableView<Worker> tableView;

    @FXML
    TableColumn<Worker, String> firstName = new TableColumn<>("Vorname");

    @FXML
    TableColumn<Worker, String> lastName = new TableColumn<>("Nachname");

    @FXML
    TableColumn<Worker, String> id = new TableColumn<>("Personalnummer");
    @FXML
    private ListView<Integer> listView = new ListView<Integer>();
    private final ObservableList<Integer> listenInhalt = FXCollections.observableArrayList();




    @FXML
    private DatePicker datum;

    @FXML
    private Label noDate;


    @FXML
    public void initialize(){


        listenInhalt.addAll(Worker.workerListFromDB);
        noDate.setVisible(false);
//        listenInhalt.add("Nummer 2");
        listView.setItems(listenInhalt);
//        listView.getItems().add("Eins");



        firstName.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        id.setCellValueFactory(new PropertyValueFactory<>("dbid"));

        lastName.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));



        tableView.getColumns().add(firstName);
        tableView.getColumns().add(lastName);
        tableView.getColumns().add(id);

        tableView.getItems().addAll(Worker.getWorkerListFromDB);

    }

    @FXML
    protected void onClickBack() throws IOException {
        Stage stage = (Stage) title.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ZeiterfassungApplication.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void holeDatum(){
        if (datum.getValue()!= null){
            System.out.println(datum.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            noDate.setVisible(false);
        }else{
            noDate.setVisible(true);
        }

    }
}
