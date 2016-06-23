package com.rubengees.jarest;

import com.mashape.unirest.http.Unirest;
import com.rubengees.jarest.controller.AbstractController;
import com.rubengees.jarest.util.ObservableCookieStore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainApp extends Application {

    private Stage primaryStage;

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
        this.primaryStage = primaryStage;

        makeStage(primaryStage, "/main.fxml");

        primaryStage.setTitle("Jarest");
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Unirest.shutdown();
    }

    @NotNull
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage makeStage(@NotNull Stage stage, @NotNull String resourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
        Pane root = loader.load();
        Scene scene = new Scene(root);

        loader.<AbstractController>getController().setMainApp(this);
        stage.setScene(scene);

        return stage;
    }
}
