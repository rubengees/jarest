package com.rubengees.jarest.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import java.util.Date;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 */
public class ObservableCookieStore implements CookieStore {
    private static ObservableCookieStore instance = new ObservableCookieStore();

    private ObservableList<Cookie> cookies = FXCollections.observableArrayList();

    private ObservableCookieStore() {

    }

    public static ObservableCookieStore getInstance() {
        return instance;
    }

    @Override
    public synchronized void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    @Override
    public ObservableList<Cookie> getCookies() {
        return cookies;
    }

    @Override
    public synchronized boolean clearExpired(Date date) {
        int sizeBefore = cookies.size();

        cookies = cookies.filtered(cookie -> cookie.isExpired(date));

        return sizeBefore != cookies.size();
    }

    @Override
    public synchronized void clear() {
        cookies.clear();
    }
}
