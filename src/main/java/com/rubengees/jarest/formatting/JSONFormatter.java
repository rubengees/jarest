package com.rubengees.jarest.formatting;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 * @date 21.06.16
 */
public class JSONFormatter implements Formatter {

    @NotNull
    @Override
    public String format(@NotNull String input) {
        try {
            if (input.startsWith("{")) {
                return new JSONObject(input).toString(4);
            } else if (input.startsWith("[")) {
                return new JSONArray(input).toString(4);
            } else {
                return input;
            }
        } catch (JSONException exception) {
            return exception.getMessage();
        }
    }
}
