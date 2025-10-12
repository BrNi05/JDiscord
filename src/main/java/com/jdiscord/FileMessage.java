package com.jdiscord;

import java.io.File;

/**
 * Class representing a file message to be sent via Discord webhook.
 * Extends MessageBase to include common message properties.
 */
public class FileMessage extends MessageBase {
    private String filepath;

    /**
     * Constructor for FileMessage.
     * @param webhook  The Discord webhook URL (optional).
     * @param username The username to display (optional).
     * @param text     The message content (optional).
     * @param filepath The path to the file to be sent (required).
     * @throws IllegalArgumentException if one or more parameters are invalid.
     */
    public FileMessage(String webhook, String username, String text, String filepath) throws IllegalArgumentException {
        super(webhook, username, text);
        setFilepath(filepath);
    }

    /**
     * Get the file path.
     * @return The file path.
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * Set the file path after validating it.
     * @param filepath The path to the file to be sent.
     * @throws IllegalArgumentException if the file path is invalid or the file does not meet criteria.
     */
    private boolean setFilepath(String filepath) throws IllegalArgumentException {
        if (filepath.isEmpty()) {
            throw new IllegalArgumentException("Select a file to send");
        }

        // Check if file exists
        File file = new File(filepath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("File does not exist");
        }

        // Max file size: 8MB (Discord API limit)
        long maxSize = 8 * 1000 * 1000L;
        if (file.length() > maxSize) {
            throw new IllegalArgumentException("File size exceeds 8MB");
        }

        this.filepath = filepath;
        return true;
    }
}
