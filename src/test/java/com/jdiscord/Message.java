package com.jdiscord;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    // Constructor test
    // which are setter tests as well

    @Test
    void validMessageSetGet() {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("key", "value"));

        Message msg = new Message(
            "https://discord.com/api/webhooks/test",
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

        // Getters tests

        assertEquals("https://example.com/avatar.png", msg.getAvatarIconUrl());
        assertEquals("Title", msg.getTitle());
        assertEquals("Description", msg.getDescription());
        assertEquals("123456", msg.getColor());
        assertEquals("https://example.com", msg.getTitleUrl());
        assertEquals("Author", msg.getAuthor());
        assertEquals("https://author.com", msg.getAuthorUrl());
        assertEquals("https://author.com/icon.png", msg.getAuthorIconUrl());
        assertEquals("https://example.com/image.png", msg.getImageUrl());
        assertEquals(fields, msg.getFields());
        assertEquals("Footer", msg.getFooterText());
        assertEquals("https://example.com/footer.png", msg.getFooterIconUrl());
        assertTrue(msg.getTimestamp());
    }

    // Setters (exception) tests

    @Test
    void settersThrowOnInvalidInput() {
        Message msg = new Message(null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, false);

        // Title length > 256
        assertThrows(IllegalArgumentException.class, () -> msg.setTitle("a".repeat(257)));

        // Description length > 2048
        assertThrows(IllegalArgumentException.class, () -> msg.setDescription("a".repeat(2049)));

        // Author length > 256
        assertThrows(IllegalArgumentException.class, () -> msg.setAuthor("a".repeat(257)));

        // Footer text > 2048
        assertThrows(IllegalArgumentException.class, () -> msg.setFooterText("a".repeat(2049)));

        // Invalid URLs
        assertThrows(IllegalArgumentException.class, () -> msg.setAvatarIconUrl("invalid-url"));
        assertThrows(IllegalArgumentException.class, () -> msg.setTitleUrl("invalid-url"));
        assertThrows(IllegalArgumentException.class, () -> msg.setAuthorUrl("invalid-url"));
        assertThrows(IllegalArgumentException.class, () -> msg.setAuthorIconUrl("invalid-url"));
        assertThrows(IllegalArgumentException.class, () -> msg.setImageUrl("invalid-url"));
        assertThrows(IllegalArgumentException.class, () -> msg.setFooterIconUrl("invalid-url"));
    }
}
