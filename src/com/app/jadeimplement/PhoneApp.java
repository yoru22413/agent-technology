package com.app.jadeimplement;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhoneApp extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("phone.fxml"));
        primaryStage.setTitle("Buy Smartphone");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }
}