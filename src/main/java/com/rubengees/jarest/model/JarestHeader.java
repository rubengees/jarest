package com.rubengees.jarest.model;

import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class JarestHeader {

    private SimpleStringProperty title;
    private SimpleStringProperty value;

    public JarestHeader(@NotNull String title, @NotNull String value) {
        this.title = new SimpleStringProperty(title);
        this.value = new SimpleStringProperty(value);
    }

    @NotNull
    public String getTitle() {
        return title.get();
    }

    @NotNull
    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title.set(title);
    }

    @NotNull
    public String getValue() {
        return value.get();
    }

    @NotNull
    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(@NotNull String value) {
        this.value.set(value);
    }
}
