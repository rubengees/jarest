package com.rubengees.jarest.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rubengees.jarest.model.JarestHeader;
import com.rubengees.jarest.util.Method;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainController {

    @FXML
    TextField urlInput;
    @FXML
    ComboBox methodComboBox;
    @FXML
    TextArea resultOutput;
    @FXML
    TableView<JarestHeader> headerOutput;
    @FXML
    Label statusOutput;

    @Nullable
    private Future<HttpResponse<String>> currentRequest = null;
    private ObservableList<JarestHeader> headers = FXCollections.observableArrayList();

    private Callback<String> defaultCallback = new Callback<String>() {
        @Override
        public void completed(HttpResponse<String> response) {
            currentRequest = null;

            Platform.runLater(() -> {
                statusOutput.setText("Request completed");
            });

            headers.clear();

            for (Map.Entry<String, List<String>> headerEntry : response.getHeaders().entrySet()) {
                headers.addAll(headerEntry.getValue().stream()
                        .map(value -> new JarestHeader(headerEntry.getKey(), value)).collect(Collectors.toList()));
            }

            prettyPrint(response.getHeaders().getFirst("Content-Type"), response.getBody());
        }

        @Override
        public void failed(UnirestException exception) {
            currentRequest = null;

            Platform.runLater(() -> {
                statusOutput.setText("Request failed");
                resultOutput.setText(exception.getMessage());
            });
        }

        @Override
        public void cancelled() {
            currentRequest = null;

            Platform.runLater(() -> {
                statusOutput.setText("Request cancelled");
            });
        }
    };

    @FXML
    public void initialize() {
        headerOutput.setItems(headers);
    }

    @FXML
    void onCloseButtonPressed() {
        Platform.exit();
    }

    @FXML
    void onActionButtonClicked() {
        if (currentRequest != null) {
            currentRequest.cancel(true);
        } else {
            if (validateInput()) {
                switch (getCurrentMethod()) {
                    case GET:
                        makeGetRequest();

                        break;
                    case POST:
                        makePostRequest();

                        break;
                }
            }
        }
    }

    private void makeGetRequest() {
        statusOutput.setText("Requesting...");

        currentRequest = Unirest.get(urlInput.getText()).asStringAsync(defaultCallback);
    }

    private boolean validateInput() {
        if (!urlInput.getText().startsWith("http")) {
            Platform.runLater(() -> resultOutput.setText("The URL must start with http"));

            return false;
        }

        return true;
    }

    private void prettyPrint(String contentType, String body) {
        String formattedResult = format(contentType, body);

        Platform.runLater(() -> resultOutput.setText(formattedResult));
    }

    @NotNull
    private String format(String contentType, String body) {
        String result;

        if (contentType.startsWith("text/html")) {
            Document document = Jsoup.parse(body);
            result = document.html();
        } else if (contentType.startsWith("application/json")) {
            try {
                JSONObject object = new JSONObject(body);

                result = object.toString(4);
            } catch (JSONException exception) {
                result = exception.getMessage();
            }
        } else {
            result = body;
        }

        return result;
    }

    private void makePostRequest() {
        statusOutput.setText("Requesting...");

        currentRequest = Unirest.post(urlInput.getText()).asStringAsync(defaultCallback);
    }

    @NotNull
    private Method getCurrentMethod() {
        switch (methodComboBox.getSelectionModel().getSelectedIndex()) {
            case 0:
                return Method.GET;
            case 1:
                return Method.POST;
            default:
                return Method.GET;
        }
    }
}
