package com.rubengees.jarest.formatting;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 */
public class FormatterFactory {

    @NotNull
    public static Formatter makeFormatter(@Nullable String contentType) {
        if (contentType == null) {
            return new DefaultFormatter();
        }

        if (contentType.startsWith("text/html")) {
            return new HTMLFormatter();
        } else if (contentType.startsWith("application/json")) {
            return new JSONFormatter();
        } else if (contentType.startsWith("text/xml") || contentType.startsWith("application/xml")) {
            return new XMLFormatter();
        } else {
            return new DefaultFormatter();
        }
    }

}
