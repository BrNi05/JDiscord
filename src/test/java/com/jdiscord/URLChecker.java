package com.jdiscord;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class URLCheckerTest {

    @Test
    void testValidHttpsUrl() {
        assertTrue(URLChecker.isValid("https://example.com"));
    }

    @Test
    void testInvalidHttpUrl() {
        assertFalse(URLChecker.isValid("http://example.com"));
    }

    @Test
    void testMalformedUrl() {
        assertFalse(URLChecker.isValid("nope"));
    }

    @Test
    void testEmptyUrl() {
        assertFalse(URLChecker.isValid(""));
    }

    @Test
    void testNullUrl() {
        assertFalse(URLChecker.isValid(null));
    }

    @Test
    void testValidWebhookUrl() {
        assertTrue(URLChecker.isWebhookUrl("https://discord.com/api/webhooks/12345/abcde"));
    }

    @Test
    void testNonWebhookHttpsUrl() {
        assertFalse(URLChecker.isWebhookUrl("https://example.com/api/webhooks/12345"));
    }

    @Test
    void testInvalidWebhookUrl() {
        assertFalse(URLChecker.isWebhookUrl("http://discord.com/api/webhooks/12345/abcde"));
    }

    @Test
    void testMalformedWebhookUrl() {
        assertFalse(URLChecker.isWebhookUrl("discord.com/api/webhooks/"));
    }
}
