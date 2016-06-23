package com.rubengees.jarest.controller;

import com.rubengees.jarest.util.ObservableCookieStore;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import org.apache.http.cookie.Cookie;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 */
public class CookieController extends AbstractController {

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
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cookieTable.setRowFactory(param -> {
            TableRow<Cookie> row = new TableRow<>();
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
        cookieTable.setItems(ObservableCookieStore.getInstance().getCookies());
    }
}
