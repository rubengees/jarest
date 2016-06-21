package com.rubengees.jarest.formatting;

import org.jetbrains.annotations.NotNull;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 * @date 21.06.16
 */
public interface Formatter {

    @NotNull
    String format(@NotNull String input) throws FormattingException;

}
