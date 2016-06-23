package com.rubengees.jarest.controller;

import com.rubengees.jarest.MainApp;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class AbstractController {

    protected MainApp mainApp;

    public void setMainApp(@NotNull MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
