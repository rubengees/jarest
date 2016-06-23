package com.rubengees.jarest.controller;

import com.rubengees.jarest.util.ObservableCookieStore;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.http.cookie.Cookie;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 */
public class CookieController {

    @FXML
    TableView<Cookie> cookieTable;
    @FXML
    TableColumn<Cookie, String> nameColumn;
    @FXML
    TableColumn<Cookie, String> valueColumn;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        valueColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
        cookieTable.setItems(ObservableCookieStore.getInstance().getCookies());
    }
}
