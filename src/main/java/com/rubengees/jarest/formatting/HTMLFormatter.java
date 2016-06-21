package com.rubengees.jarest.formatting;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 * @date 21.06.16
 */
public class HTMLFormatter implements Formatter {

    @NotNull
    @Override
    public String format(@NotNull String input) {
        Document document = Jsoup.parse(input);
        return document.html();
    }
}
