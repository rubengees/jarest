package com.rubengees.jarest.formatting;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 */
public class HTMLFormatter implements Formatter {

    @NotNull
    @Override
    public String format(@NotNull String input) throws FormattingException {
        try {
            Document document = Jsoup.parse(input);

            return document.html();
        } catch (Exception exception) {
            throw new FormattingException();
        }
    }
}
