package com.rubengees.jarest;

import com.mashape.unirest.http.Unirest;
import com.rubengees.jarest.util.ObservableCookieStore;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.HttpAsyncClients;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainApp extends Application {

    public static void main(String[] args) {
        Unirest.setHttpClient(HttpClients.custom()
                .setDefaultCookieStore(ObservableCookieStore.getInstance())
                .build());
        Unirest.setAsyncHttpClient(HttpAsyncClients.custom()
                .setDefaultCookieStore(ObservableCookieStore.getInstance())
                .build());

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Jarest");

        Pane root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Unirest.shutdown();
    }
}
