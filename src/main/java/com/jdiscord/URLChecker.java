package com.jdiscord;

import java.net.URI;
import java.net.URL;

/**
 * Utility class for URL validation.
 */
public class URLChecker {

    /**
     * Checks if the provided URL is a valid HTTPS URL.
     * @param _url The URL string to validate.
     * @return true if the URL is valid and uses HTTPS, false otherwise.
     */
    public static boolean isValid(String _url) {
        try {
            URL url = new URI(_url).toURL();
            return url.getProtocol().equals("https");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the provided URL is a valid Discord webhook URL.
     * @param url The URL string to validate.
     * @return true if the URL is a valid Discord webhook URL, false otherwise.
     */
    public static boolean isWebhookUrl(String url) {
        if (isValid(url)) {
            return url.startsWith("https://discord.com/api/webhooks/");
        }

        return false;
    }
}
