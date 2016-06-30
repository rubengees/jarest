package com.rubengees.jarest.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rubengees.jarest.formatting.FormatterFactory;
import com.rubengees.jarest.formatting.FormattingException;
import com.rubengees.jarest.model.JarestFormParameter;
import com.rubengees.jarest.model.JarestHeader;
import com.rubengees.jarest.model.JarestQueryParameter;
import com.rubengees.jarest.util.Method;
import com.rubengees.jarest.util.ObservableCookieStore;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainController extends AbstractController {

    @FXML
    TableView<JarestQueryParameter> queryInput;
    @FXML
    TextField queryParameterTitleInput;
    @FXML
    TextField queryParameterValueInput;

    @FXML
    TitledPane formInputRoot;
    @FXML
    TableView<JarestFormParameter> formInput;
    @FXML
    TextField formParameterTitleInput;
    @FXML
    TextField formParameterValueInput;

    @FXML
    TextField urlInput;

    @FXML
    ComboBox methodComboBox;

    @FXML
    Button actionButton;

    @FXML
    TextArea resultOutput;
    @FXML
    TableView<JarestHeader> headerOutput;
    @FXML
    Label statusOutput;

    @Nullable
    private Future<HttpResponse<String>> currentRequest = null;
    @NotNull
    private ObservableList<JarestHeader> headers = FXCollections.observableArrayList();
    @NotNull
    private ObservableList<JarestQueryParameter> queryParameters = FXCollections.observableArrayList();
    @NotNull
    private ObservableList<JarestFormParameter> formParameters = FXCollections.observableArrayList();

    @NotNull
    private Callback<String> defaultCallback = new Callback<String>() {
        @Override
        public void completed(HttpResponse<String> response) {
            currentRequest = null;

            for (Map.Entry<String, List<String>> headerEntry : response.getHeaders().entrySet()) {
                headers.addAll(headerEntry.getValue().stream()
                        .map(value -> new JarestHeader(headerEntry.getKey(), value)).collect(Collectors.toList()));
            }

            Platform.runLater(() -> {
                statusOutput.setText("Request completed: " + response.getStatus() + " " + response.getStatusText());
                actionButton.setText("Send");
            });

            prettyPrint(response.getHeaders().getFirst("Content-Type"), response.getBody());
        }

        @Override
        public void failed(UnirestException exception) {
            currentRequest = null;

            Platform.runLater(() -> {
                statusOutput.setText("Request failed: " + exception.getMessage());
                actionButton.setText("Send");
            });
        }

        @Override
        public void cancelled() {
            currentRequest = null;

            Platform.runLater(() -> {
                statusOutput.setText("Request cancelled");
                actionButton.setText("Send");
            });
        }
    };

    @FXML
    public void initialize() {
        headerOutput.setItems(headers);
        queryInput.setItems(queryParameters);
        formInput.setItems(formParameters);

        queryInput.setRowFactory(param -> {
            TableRow<JarestQueryParameter> row = new TableRow<>();
            ContextMenu rowMenu = new ContextMenu();
            MenuItem removeItem = new MenuItem("Delete");

            removeItem.setOnAction(event -> param.getItems().remove(row.getItem()));
            rowMenu.getItems().add(removeItem);

            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null));
            return row;
        });

        formInput.setRowFactory(param -> {
            TableRow<JarestFormParameter> row = new TableRow<>();
            ContextMenu rowMenu = new ContextMenu();
            MenuItem removeItem = new MenuItem("Delete");

            removeItem.setOnAction(event -> param.getItems().remove(row.getItem()));
            rowMenu.getItems().add(removeItem);

            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null));
            return row;
        });
    }

    @FXML
    void onCloseButtonClick() {
        Platform.exit();
    }

    @FXML
    void onActionButtonClick() {
        makeRequest();
    }

    @FXML
    void onUrlInputEnter() {
        makeRequest();
    }

    @FXML
    void onAddQueryParameter() {
        if (!queryParameterTitleInput.getText().isEmpty() && !queryParameterValueInput.getText().isEmpty()) {
            queryParameters.add(new JarestQueryParameter(queryParameterTitleInput.getText(),
                    queryParameterValueInput.getText()));

            queryParameterTitleInput.clear();
            queryParameterValueInput.clear();
        }
    }

    @FXML
    void onAddFormParameter() {
        if (!formParameterTitleInput.getText().isEmpty() && !formParameterValueInput.getText().isEmpty()) {
            formParameters.add(new JarestFormParameter(formParameterTitleInput.getText(),
                    formParameterValueInput.getText()));

            formParameterTitleInput.clear();
            formParameterValueInput.clear();
        }
    }

    @FXML
    void onViewCookiesClick() {
        try {
            Stage stage = new Stage();

            mainApp.makeStage(stage, "/cookies.fxml");

            stage.initStyle(StageStyle.UNIFIED);
            stage.initOwner(mainApp.getPrimaryStage());
            stage.setTitle("Cookies");
            stage.show();
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);

            errorAlert.setTitle("Error Report");
            errorAlert.setHeaderText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @FXML
    void onClearCookies() {
        ObservableCookieStore.getInstance().clear();
    }

    private void prettyPrint(@Nullable String contentType, @NotNull String body) {
        try {
            String formattedResult = FormatterFactory.makeFormatter(contentType).format(body);

            Platform.runLater(() -> resultOutput.setText(formattedResult));
        } catch (FormattingException exception) {
            Platform.runLater(() -> statusOutput.setText(exception.getMessage()));
        }
    }

    private void makeRequest() {
        if (currentRequest != null) {
            currentRequest.cancel(true);
        } else {
            try {
                switch (getCurrentMethod()) {
                    case GET:
                        makeGetRequest(urlInput.getText());

                        break;
                    case POST:
                        makePostRequest(urlInput.getText());

                        break;
                }

                headers.clear();

                Platform.runLater(() -> {
                    statusOutput.setText("Requesting...");
                    resultOutput.clear();
                    actionButton.setText("Cancel");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusOutput.setText(e.getMessage());
                    actionButton.setText("Send");
                });
            }
        }
    }

    private void makeGetRequest(@NotNull String url) throws Exception {
        currentRequest = Unirest.get(url).queryString(getQueryParameters()).asStringAsync(defaultCallback);
    }

    private void makePostRequest(@NotNull String url) throws Exception {
        currentRequest = Unirest.post(url).queryString(getQueryParameters()).fields(getFormParameters())
                .asStringAsync(defaultCallback);
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

    @NotNull
    private Map<String, Object> getQueryParameters() {
        Map<String, Object> result = new HashMap<>();

        for (JarestQueryParameter queryParameter : queryParameters) {
            result.put(queryParameter.getTitle(), queryParameter.getValue());
        }

        return result;
    }

    @NotNull
    private Map<String, Object> getFormParameters() {
        Map<String, Object> result = new HashMap<>();

        for (JarestFormParameter formParameter : formParameters) {
            result.put(formParameter.getTitle(), formParameter.getValue());
        }

        return result;
    }
}
