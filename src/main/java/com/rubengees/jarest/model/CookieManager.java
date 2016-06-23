package com.rubengees.jarest.model;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 */
public final class CookieManager {

    private static CookieStore cookieStore = new BasicCookieStore();

    public static void clearCookies() {
        cookieStore.clear();

    }

    @NotNull
    public static List<Cookie> getCookies() {
        return cookieStore.getCookies();
    }

    @NotNull
    public static CookieStore getCookieStore() {
        return cookieStore;
    }

}
