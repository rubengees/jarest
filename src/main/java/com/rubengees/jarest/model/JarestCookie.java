package com.rubengees.jarest.model;

import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 */
public class JarestCookie {

    private SimpleStringProperty name;
    private SimpleStringProperty value;

    public JarestCookie(@NotNull String name, @NotNull String value) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleStringProperty(value);
    }

    @NotNull
    public String getName() {
        return name.get();
    }

    public void setName(@NotNull String name) {
        this.name.set(name);
    }

    @NotNull
    public SimpleStringProperty nameProperty() {
        return name;
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
