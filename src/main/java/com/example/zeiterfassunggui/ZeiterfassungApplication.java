package com.example.zeiterfassunggui;

import com.example.zeiterfassunggui.classes.Datenbank;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;

import javax.swing.*;

public class ZeiterfassungApplication extends Application {


//    private java.awt.Image loadImage(String filnename){
//        URL url = getClass().getClassLoader().getResource(filnename);
//        return new ImageIcon(url).getImage();
//    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ZeiterfassungApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);


//        com.apple.eawt.Application App = com.apple.eawt.Application.getApplication();
//        java.awt.Image icon = loadImage("icon/icon.png");
//        App.setDockIconImage(icon);


        stage.setTitle("Zeiterfassung");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(ZeiterfassungApplication.class.getResourceAsStream("icon/icon.png"))));
//      stage.setFullScreen(true);
        stage.show();

    }

    public static Datenbank getdb(){
        return new Datenbank();
    }

    public static void main(String[] args) throws SQLException {
        launch();

    }
}