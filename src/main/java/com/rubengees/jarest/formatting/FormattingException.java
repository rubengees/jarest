package com.rubengees.jarest.formatting;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class FormattingException extends Exception {

    public FormattingException() {
        super("The response was not correctly formatted");
    }
}
