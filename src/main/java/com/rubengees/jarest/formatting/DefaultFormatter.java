package com.rubengees.jarest.formatting;

import org.jetbrains.annotations.NotNull;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 * @date 21.06.16
 */
public class DefaultFormatter implements Formatter {
    @NotNull
    @Override
    public String format(@NotNull String input) {
        return input;
    }
}
