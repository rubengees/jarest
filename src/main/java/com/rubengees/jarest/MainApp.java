package com.rubengees.jarest;

import com.mashape.unirest.http.Unirest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Jarest");

        Pane root = FXMLLoader.load(getClass().getResource("/main.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Unirest.shutdown();
    }
}
