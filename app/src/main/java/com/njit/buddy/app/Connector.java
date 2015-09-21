package com.njit.buddy.app;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.*;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author toyknight 8/14/2015.
 */
public class Connector {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String COOKIES_HEADER = "Set-Cookie";
    private static final String DEBUG_TAG = "DEBUG";

    private static CookieManager cookie_manager;

    private Connector() {
    }

    public static void initialize() {
        Connector.cookie_manager = new CookieManager();
    }

    /**
     * Send a GET request.
     *
     * @param url the request url
     * @return the response content
     * @throws IOException
     */
    public static String executeGet(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(10000);
        connection.setRequestMethod(GET);
        connection.setDoInput(true);
        attachCookie(connection);
        connection.connect();
        int response = connection.getResponseCode();
        Log.d(DEBUG_TAG, "The response code for [GET] '" + url + "' is " + response);

        String content = getContent(connection.getInputStream());
        updateCookieStore(connection);
        connection.disconnect();
        return content;
    }

    /**
     * Send a POST request.
     *
     * @param url the request url
     * @return the response content
     * @throws IOException
     */
    public static String executePost(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(10000);
        connection.setRequestMethod(POST);
        connection.setDoInput(true);
        attachCookie(connection);
        connection.connect();
        int response = connection.getResponseCode();
        Log.d(DEBUG_TAG, "The response code for [POST] '" + url + "' is " + response);

        String content = getContent(connection.getInputStream());
        updateCookieStore(connection);
        connection.disconnect();
        return content;
    }

    private static String getContent(InputStream is) throws IOException {
        InputStreamReader ir = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(ir);
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = br.readLine()) != null) {
            content.append(line).append("\n");
        }
        br.close();
        return content.toString();
    }

    private static void updateCookieStore(HttpURLConnection connection) {
        List<String> cookies = connection.getHeaderFields().get(COOKIES_HEADER);
        if (cookies != null) {
            for (String cookie : cookies) {
                cookie_manager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
    }

    private static void attachCookie(HttpURLConnection connection) {
        if (cookie_manager.getCookieStore().getCookies().size() > 0) {
            connection.setRequestProperty("Cookie", TextUtils.join(";", cookie_manager.getCookieStore().getCookies()));
        }
    }

}
