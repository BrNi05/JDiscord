package com.jdiscord;

import java.io.IOException;

import java.net.URI;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Utility class for URL validation.
 */
public class URLChecker {
    /**
     * Utility class; prevent instantiation.
     */
    private URLChecker() { }

    // Okhttp3 client instance
    private static final OkHttpClient CLIENT = new OkHttpClient();

    // Toggle for reachability check
    private static boolean checkReachability = true;

    /**
     * Checks if the provided URL is a valid HTTPS URL.
     * @param url The URL string to validate.
     * @return true if the URL is valid and uses HTTPS, false otherwise.
     */
    public static boolean isValid(String url) {
        try {
            URL tryUrl = new URI(url).toURL();
            boolean result = tryUrl.getProtocol().equals("https");

            if (!checkReachability) return result;

            Request request = new Request.Builder().url(url).get().build();

            try (Response response = CLIENT.newCall(request).execute()) {
                return result && response.isSuccessful();
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the provided URL is a valid Discord webhook URL.
     * Performs a GET request and checks if the response code is 200.
     * @param url The URL string to validate.
     * @return true if the URL is a valid Discord webhook URL, false otherwise.
     */
    public static boolean isWebhookUrl(String url) {
        if (url == null || !url.startsWith("https://discord.com/api/webhooks/")) return false;

        if (!checkReachability) return true;

        Request request = new Request.Builder().url(url).get().build();

        try (Response response = CLIENT.newCall(request).execute()) {
            return response.code() == 200;
        } catch (IOException e) {
            return false; // network error or invalid URL
        }
    }

    /**
     * Sets whether to check URL reachability in isWebhookUrl method.
     * Should only be used for testing purposes.
     * @param check true to enable reachability check, false to disable.
     */
    public static void setCheckReachability(boolean check) {
        checkReachability = check;
    }
}
