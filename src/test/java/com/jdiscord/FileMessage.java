package com.jdiscord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileMessageTest {

    private static final String VALID_WEBHOOK = "https://discord.com/api/webhooks/123/abc";

    @TempDir
    Path tempDir;

    // Constructor test

    @Test
    void validFile() throws IOException {
        File tempFile = tempDir.resolve("test.txt").toFile();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            out.write("test".getBytes());
        }

        FileMessage msg = new FileMessage(VALID_WEBHOOK, "user", "text", tempFile.getAbsolutePath());
        assertEquals(tempFile.getAbsolutePath(), msg.getFilepath());
    }

    // Constructor exception tests

    @Test
    void emptyFilePathThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            new FileMessage(VALID_WEBHOOK, "user", "text", "")
        );
        assertEquals("Select a file to send", ex.getMessage());
    }

    @Test
    void nonExistentFileThrows() {
        String fakePath = tempDir.resolve("does_not_exist.txt").toString();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            new FileMessage(VALID_WEBHOOK, "user", "text", fakePath)
        );
        assertEquals("File does not exist", ex.getMessage());
    }

    @Test
    void pathIsDirectoryThrows() {
        File dir = tempDir.resolve("subdir").toFile();
        dir.mkdir();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            new FileMessage(VALID_WEBHOOK, "user", "text", dir.getAbsolutePath())
        );
        assertEquals("File does not exist", ex.getMessage());
    }

    @Test
    void fileTooLargeThrows() throws IOException {
        File largeFile = tempDir.resolve("large.dat").toFile();
        try (FileOutputStream out = new FileOutputStream(largeFile)) {
            out.write(new byte[8 * 1000 * 1000 + 1]);
        }

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            new FileMessage(VALID_WEBHOOK, "user", "text", largeFile.getAbsolutePath())
        );
        assertEquals("File size exceeds 8MB", ex.getMessage());
    }
}
