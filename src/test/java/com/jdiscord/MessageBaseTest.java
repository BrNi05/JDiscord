package com.jdiscord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class MessageBaseTest {

    // MessageBase abstract class cannot be instantiated directly
    private static class TestMessage extends MessageBase {
        public TestMessage(String webhook, String username, String text) {
            super(webhook, username, text);
        }
    }

    // Set URLChecker to not check reachability during tests
    @BeforeEach
    void disableReachabilityCheck() {
        URLChecker.setCheckReachability(false);
    }

    // Constructor test (webhook)

    @Test
    void validWebhook() {
        TestMessage msg = new TestMessage(
            "https://discord.com/api/webhooks/123/abc",
            "user",
            "Hello"
        );
        assertEquals("https://discord.com/api/webhooks/123/abc", msg.getWebhook());
    }

    // Exception tests

    @Test
    void invalidWebhookThrows() {
        assertThrows(IllegalArgumentException.class, () -> 
        new TestMessage("https://example.com/not-webhook", "user", "Hello"));
    }

    @Test
    void nullWebhookIsAllowed() {
        TestMessage msg = new TestMessage(null, "user", "Hello");
        assertNull(msg.getWebhook());
    }

    // Constructor test (username)

    @Test
    void usernameWithinLimit() {
        TestMessage msg = new TestMessage(
            "https://discord.com/api/webhooks/123/abc",
            "user",
            "Hello"
        );
        assertEquals("user", msg.getUsername());
    }

    // Exception tests

    @Test
    void usernameTooLongThrows() {
        String longName = "a".repeat(33);
        assertThrows(IllegalArgumentException.class, () ->
        new TestMessage("https://discord.com/api/webhooks/123/abc", longName, "Hello"));
    }

    @Test
    void emptyUsernameBecomesNull() {
        TestMessage msg = new TestMessage(
            "https://discord.com/api/webhooks/123/abc",
            "",
            "Hello"
        );
        assertNull(msg.getUsername());
    }

    @Test
    void nullUsernameIsAllowed() {
        TestMessage msg = new TestMessage(
            "https://discord.com/api/webhooks/123/abc",
            null,
            "Hello"
        );
        assertNull(msg.getUsername());
    }

    // Constructor test (text)

    @Test
    void textWithinLimit() {
        String text = "Hello world";
        TestMessage msg = new TestMessage(
            "https://discord.com/api/webhooks/123/abc",
            "user",
            text
        );
        assertEquals(text, msg.getText());
    }

    // Exception tests

    @Test
    void textTooLongThrows() {
        String longText = "a".repeat(2001);
        assertThrows(IllegalArgumentException.class, () ->
        new TestMessage("https://discord.com/api/webhooks/123/abc", "user", longText));
    }

    @Test
    void emptyTextBecomesNull() {
        TestMessage msg = new TestMessage(
            "https://discord.com/api/webhooks/123/abc",
            "user",
            ""
        );
        assertNull(msg.getText());
    }

    @Test
    void nullTextIsAllowed() {
        TestMessage msg = new TestMessage(
            "https://discord.com/api/webhooks/123/abc",
            "user",
            null
        );
        assertNull(msg.getText());
    }
}
