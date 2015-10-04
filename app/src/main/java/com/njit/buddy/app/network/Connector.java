package com.njit.buddy.app.network;

import android.text.TextUtils;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.List;

/**
 * @author toyknight 8/14/2015.
 */
public class Connector {

    public static final String SERVER_ADDRESS = "http://54.174.64.238:8080";

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String COOKIES_HEADER = "Set-Cookie";
    private static final String DEBUG_TAG = "Network";

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
     * @param content the request body
     * @return the response content
     * @throws IOException
     */
    public static String executeGet(String url, String content) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestMethod(GET);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        attachCookie(connection);
        connection.connect();

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(content);
        writer.flush();

        int code = connection.getResponseCode();
        Log.d(DEBUG_TAG, "The response code for [GET] '" + url + "' is " + code);

        String response = getContent(connection);
        updateCookieStore(connection);
        connection.disconnect();
        return response;
    }

    /**
     * Send a POST request.
     *
     * @param url the request url
     * @param content the request body
     * @return the response content
     * @throws IOException
     */
    public static String executePost(String url, String content) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestMethod(POST);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        attachCookie(connection);
        connection.connect();

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(content);
        writer.flush();

        int code = connection.getResponseCode();
        Log.d(DEBUG_TAG, "The response code for [POST] '" + url + "' is " + code);

        String response = getContent(connection);
        updateCookieStore(connection);
        connection.disconnect();
        return response;
    }

    private static String getContent(URLConnection connection) throws IOException {
        InputStreamReader ir = new InputStreamReader(connection.getInputStream(), "UTF-8");
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
