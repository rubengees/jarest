package com.rubengees.jarest.model;

import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 */
public class JarestFormParameter {

    private SimpleStringProperty title;
    private SimpleStringProperty value;

    public JarestFormParameter(@NotNull String title, @NotNull String value) {
        this.title = new SimpleStringProperty(title);
        this.value = new SimpleStringProperty(value);
    }

    @NotNull
    public String getTitle() {
        return title.get();
    }

    public void setTitle(@NotNull String title) {
        this.title.set(title);
    }

    @NotNull
    public SimpleStringProperty titleProperty() {
        return title;
    }

    @NotNull
    public String getValue() {
        return value.get();
    }

    public void setValue(@NotNull String value) {
        this.value.set(value);
    }

    @NotNull
    public SimpleStringProperty valueProperty() {
        return value;
    }

}
