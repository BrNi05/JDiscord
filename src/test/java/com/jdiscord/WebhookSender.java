package com.jdiscord;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;

import java.lang.reflect.Method;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

import java.util.List;
import java.util.ArrayList;

class WebhookSenderTest {

    @Test
    void constructorValidWebhook() {
        String validWebhook = "https://discord.com/api/webhooks/1234567890/abcdefghijklmnopqrstuvwxyz";
        assertDoesNotThrow(() -> new WebhookSender(validWebhook));
    }

    @Test
    void constructorInvalidWebhook() {
        String invalidWebhook = "https://example.com/not-a-webhook";
        assertThrows(IllegalArgumentException.class, () -> new WebhookSender(invalidWebhook));

        String malformedWebhook = "not-a-url";
        assertThrows(IllegalArgumentException.class, () -> new WebhookSender(malformedWebhook));

        assertThrows(IllegalArgumentException.class, () -> new WebhookSender(null));
    }

    @Test
    void buildMessagePayloadProducesJson() throws Exception {
        String validWebhook = "https://discord.com/api/webhooks/123/abc";
        WebhookSender sender = new WebhookSender(validWebhook);

        List<Field> fields = new ArrayList<>();
        fields.add(new Field("key", "value"));

        Message msg = new Message(
            validWebhook,
            "username",
            "https://example.com/avatar.png",
            "Hello world",
            "Title",
            "Description",
            "123456",
            "https://example.com",
            "Author",
            "https://author.com",
            "https://author.com/icon.png",
            "https://example.com/image.png",
            fields,
            "Footer",
            "https://example.com/footer.png",
            true
        );

        Method method = WebhookSender.class.getDeclaredMethod("buildPayload", Message.class);
        method.setAccessible(true);
        String json = (String) method.invoke(sender, msg);

        assertNotNull(json);
        assertTrue(json.contains("Hello world"));
        assertTrue(json.contains("Title"));
        assertTrue(json.contains("123456"));
        assertTrue(json.contains("Author"));
        assertTrue(json.contains("Footer"));
    }

    @TempDir
    Path tempDir;

    @Test
    void buildFilePayloadProducesJson() throws Exception {
        String validWebhook = "https://discord.com/api/webhooks/123/abc";
        WebhookSender sender = new WebhookSender(validWebhook);

        File tempFile = tempDir.resolve("test.txt").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("test");
        }

        FileMessage fm = new FileMessage(validWebhook, "username", "Text", tempFile.getAbsolutePath());

        var method = WebhookSender.class.getDeclaredMethod("buildFilePayload", FileMessage.class);
        method.setAccessible(true);
        String json = (String) method.invoke(sender, fm);

        assertNotNull(json);
        assertTrue(json.contains("Text"));
        assertTrue(json.contains("username"));
    }

    // Test send message process
    // Expect an exception due to invalid webhook URL (or no network)
    @Test
    void sendMessageInvalidWebhook() throws Exception {
        String validWebhook = "https://discord.com/api/webhooks/123/abc";
        WebhookSender sender = new WebhookSender(validWebhook);

        Message msg = new Message(
            validWebhook,
            "username",
            null,
            null,
            "Title",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            List.of(),
            null,
            null,
            true
        );

        assertThrows(Exception.class, () -> sender.sendMessage(msg));
    }

    // Test send file message process
    // Expect an exception due to invalid webhook URL (or no network)
    @Test
    void sendFileInvalidWebhook() throws Exception {
        String validWebhook = "https://discord.com/api/webhooks/123/abc";
        WebhookSender sender = new WebhookSender(validWebhook);

        // Mimic an existing file in tempDir
        File tempFile = tempDir.resolve("test.txt").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("test");
        }

        FileMessage fm = new FileMessage(validWebhook, "username", "Text", tempFile.getAbsolutePath());

        assertThrows(Exception.class, () -> sender.sendFile(fm));
    }
}
