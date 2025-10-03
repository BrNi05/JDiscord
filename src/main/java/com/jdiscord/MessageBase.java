package com.jdiscord;

/**
 * Abstract base class for messages.
 * Contains common properties: webhook, username, and text.
 */
public abstract class MessageBase {
    protected String webhook;
    protected String username;
    protected String text;

    /**
     * Constructor for MessageBase.
     * @param webhook  The Discord webhook URL.
     * @param username The username to display.
     * @param text     The message content.
     * @throws IllegalArgumentException if one or more parameters are invalid.
     */
    public MessageBase(String webhook, String username, String text) throws IllegalArgumentException {
        setWebhook(webhook);
        setUsername(username);
        setText(text);
    }

    /**
     * Get the webhook URL.
     * @return The webhook URL.
     */
    public String getWebhook() {
        return webhook;
    }

    /**
     * Get the username.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the text message.
     * @return The text message.
     */
    public String getText() {
        return text;
    }

    /**
     * Set the webhook URL after validating it.
     * @param webhook The Discord webhook URL.
     * @throws IllegalArgumentException if the webhook URL is invalid.
     */
    private void setWebhook(String webhook) throws IllegalArgumentException {
        if (webhook == null) return;

        if (URLChecker.isWebhookUrl(webhook)) {
            this.webhook = webhook;
        } else {
            throw new IllegalArgumentException("Invalid webhook URL");
        }
    }

    /**
     * Set the username after validating its length.
     * @param username The username to display.
     * @throws IllegalArgumentException if the username exceeds 32 characters.
     */
    private void setUsername(String username) throws IllegalArgumentException {
        if (username == null) return;
        if (username.length() > 32) throw new IllegalArgumentException("Username must be 32 characters or less");
        this.username = username.isEmpty() ? null : username;
    }

    /**
     * Set the text message after validating its length.
     * @param text The message content.
     * @throws IllegalArgumentException if the text exceeds 2000 characters.
     */
    private void setText(String text) throws IllegalArgumentException {
        if (text == null) return;
        if (text.length() > 2000) throw new IllegalArgumentException("Message must be 2000 characters or less");
        this.text = text.isEmpty() ? null : text;
    }
}
