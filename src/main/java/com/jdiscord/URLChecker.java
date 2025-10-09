package com.jdiscord;

import java.net.URI;
import java.net.URL;

/**
 * Utility class for URL validation.
 */
public class URLChecker {
    /**
     * Utility class; prevent instantiation.
     */
    private URLChecker() { }

    /**
     * Checks if the provided URL is a valid HTTPS URL.
     * @param url The URL string to validate.
     * @return true if the URL is valid and uses HTTPS, false otherwise.
     */
    public static boolean isValid(String url) {
        try {
            URL tryUrl = new URI(url).toURL();
            return tryUrl.getProtocol().equals("https");
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
