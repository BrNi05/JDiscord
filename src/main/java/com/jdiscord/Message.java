package com.jdiscord;

import java.util.List;

/**
 * Class representing a Discord message with rich embed features.
 * Extends MessageBase to include common message properties.
 */
public class Message extends MessageBase {
    private String avatarIconUrl = null;
    private String title = null;
    private String description = null;
    private String color = null; // decimal
    private String titleUrl = null;
    private String author = null;
    private String authorUrl = null;
    private String authorIconUrl = null;
    private String imageUrl = null;
    private List<Field> fields;
    private String footerText = null;
    private String footerIconUrl = null;
    private boolean timestamp = false;

    /**
     * Constructor for Message.
     * @param webhook       The Discord webhook URL (optional).
     * @param username      The username to display (optional).
     * @param avatarIconUrl The avatar icon URL (optional).
     * @param text          The message content (optional).
     * @param title         The embed title (optional, max 256 characters).
     * @param description   The embed description (optional, max 2048 characters).
     * @param color         The embed color in decimal or hex format (optional).
     * @param titleUrl      The URL for the embed title (optional).
     * @param author        The embed author name (optional, max 256 characters).
     * @param authorUrl     The URL for the embed author (optional).
     * @param authorIconUrl The URL for the embed author icon (optional).
     * @param imageUrl      The URL for the embed image (optional).
     * @param fields        A list of Field objects for the embed (optional).
     * @param footerText    The embed footer text (optional, max 2048 characters).
     * @param footerIconUrl The URL for the embed footer icon (optional).
     * @param timestamp     Whether to include a timestamp in the embed (true/false).
     * @throws IllegalArgumentException if one or more parameters are invalid.
     */
    public Message (
        String webhook,
        String username,
        String avatarIconUrl,
        String text,
        String title,
        String description,
        String color,
        String titleUrl,
        String author,
        String authorUrl,
        String authorIconUrl,
        String imageUrl,
        List<Field> fields,
        String footerText,
        String footerIconUrl,
        boolean timestamp) throws IllegalArgumentException {
        super(webhook, username, text);
        setAvatarIconUrl(avatarIconUrl);
        setTitle(title);
        setDescription(description);
        setColor(color);
        setTitleUrl(titleUrl);
        setAuthor(author);
        setAuthorUrl(authorUrl);
        setAuthorIconUrl(authorIconUrl);
        setImageUrl(imageUrl);
        setFields(fields);
        setFooterText(footerText);
        setFooterIconUrl(footerIconUrl);
        this.timestamp = timestamp;
    }

    /**
     * Get the avatar icon URL.
     * @return The avatar icon URL.
     */
    public String getAvatarIconUrl() {
        return avatarIconUrl;
    }

    /**
     * Get the embed title.
     * @return The embed title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the embed description.
     * @return The embed description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the embed color in decimal.
     * @return The embed color in decimal (as a string).
     */
    public String getColor() {
        return color;
    }

    /**
     * Get the embed title URL.
     * @return The embed title URL.
     */
    public String getTitleUrl() {
        return titleUrl;
    }

    /**
     * Get the embed author name.
     * @return The embed author name.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Get the embed author URL.
     * @return The embed author URL.
     */
    public String getAuthorUrl() {
        return authorUrl;
    }

    /**
     * Get the embed author icon URL.
     * @return The embed author icon URL.
     */
    public String getAuthorIconUrl() {
        return authorIconUrl;
    }

    /**
     * Get the embed image URL.
     * @return The embed image URL.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Get the list of embed fields.
     * @return The list of embed fields.
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * Get the embed footer text.
     * @return The embed footer text.
     */
    public String getFooterText() {
        return footerText;
    }

    /**
     * Get the embed footer icon URL.
     * @return The embed footer icon URL.
     */
    public String getFooterIconUrl() {
        return footerIconUrl;
    }

    /**
     * Check if the embed includes a timestamp.
     * @return True if the embed includes a timestamp, false otherwise.
     */
    public boolean getTimestamp() {
        return timestamp;
    }

    /**
     * Set the avatar icon URL.
     * @param avatarIconUrl The avatar icon URL (optional).
     * @throws IllegalArgumentException if the URL is invalid.
     */
    public void setAvatarIconUrl(String avatarIconUrl) throws IllegalArgumentException {
        if (avatarIconUrl == null) return;

        if (URLChecker.isValid(avatarIconUrl)) {
            this.avatarIconUrl = avatarIconUrl;
        } else if (!avatarIconUrl.isEmpty()) {
            throw new IllegalArgumentException("Invalid avatar icon URL");
        }
    }

    /**
     * Set the embed title.
     * @param title The embed title (optional, max 256 characters).
     * @throws IllegalArgumentException if the title exceeds 256 characters.
     */
    public void setTitle(String title) throws IllegalArgumentException {
        if (title == null) return;

        if (title.length() <= 256) {
            this.title = title;
        } else {
            throw new IllegalArgumentException("Title must be 256 characters or less");
        }
    }

    /**
     * Set the embed description.
     * @param description The embed description.
     * @throws IllegalArgumentException if the description exceeds 2048 characters. 
     */
    public void setDescription(String description) throws IllegalArgumentException {
        if (description == null) return;

        if (description.length() <= 2048) {
            this.description = description;
        } else {
            throw new IllegalArgumentException("Description must be 2048 characters or less");
        }
    }

    /**
     * Set the embed color.
     * @param color The embed color (in decimal format).
     */
    public void setColor(String color) throws IllegalArgumentException {
        this.color = color;
    }

    /**
     * Set the embed title URL.
     * @param titleUrl The embed title URL.
     * @throws IllegalArgumentException if the URL is invalid.
     */
    public void setTitleUrl(String titleUrl) throws IllegalArgumentException {
        if (titleUrl == null) return;

        if (URLChecker.isValid(titleUrl)) {
            this.titleUrl = titleUrl;
        } else if (!titleUrl.isEmpty()) {
            throw new IllegalArgumentException("Invalid title URL");
        }
    }

    /**
     * Set the embed author name.
     * @param author The embed author name.
     * @throws IllegalArgumentException if the author name exceeds 256 characters.
     */
    public void setAuthor(String author) throws IllegalArgumentException {
        if (author == null) return;

        if (author.length() <= 256) {
            this.author = author;
        } else {
            throw new IllegalArgumentException("Author name must be 256 characters or less");
        }
    }

    /**
     * Set the embed author URL.
     * @param authorUrl The embed author URL.
     * @throws IllegalArgumentException if the URL is invalid.
     */
    public void setAuthorUrl(String authorUrl) throws IllegalArgumentException {
        if (authorUrl == null) return;

        if (URLChecker.isValid(authorUrl)) {
            this.authorUrl = authorUrl;
        } else if (!authorUrl.isEmpty()) {
            throw new IllegalArgumentException("Invalid author URL");
        }
    }

    /**
     * Set the embed author icon URL.
     * @param authorIconUrl The embed author icon URL.
     * @throws IllegalArgumentException if the URL is invalid.
     */
    public void setAuthorIconUrl(String authorIconUrl) throws IllegalArgumentException {
        if (authorIconUrl == null) return;

        if (URLChecker.isValid(authorIconUrl)) {
            this.authorIconUrl = authorIconUrl;
        } else if (!authorIconUrl.isEmpty()) {
            throw new IllegalArgumentException("Invalid author icon URL");
        }
    }

    /**
     * Set the embed image URL.
     * @param imageUrl The embed image URL.
     * @throws IllegalArgumentException if the URL is invalid.
     */
    public void setImageUrl(String imageUrl) throws IllegalArgumentException {
        if (imageUrl == null) return;

        if (URLChecker.isValid(imageUrl)) {
            this.imageUrl = imageUrl;
        } else if (!imageUrl.isEmpty()) {
            throw new IllegalArgumentException("Invalid image URL");
        }
    }

    /**
     * Set the list of embed fields.
     * @param fields The list of embed fields.
     */
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    /**
     * Set the embed footer text.
     * @param footerText The embed footer text.
     * @throws IllegalArgumentException if the footer text exceeds 2048 characters.
     */
    public void setFooterText(String footerText) throws IllegalArgumentException {
        if (footerText == null) return;

        if (footerText.length() <= 2048) {
            this.footerText = footerText;
        } else {
            throw new IllegalArgumentException("Footer text must be 2048 characters or less");
        }
    }

    /**
     * Set the embed footer icon URL.
     * @param footerIconUrl The embed footer icon URL.
     * @throws IllegalArgumentException if the URL is invalid.
     */
    public void setFooterIconUrl(String footerIconUrl) throws IllegalArgumentException {
        if (footerIconUrl == null) return;

        if (URLChecker.isValid(footerIconUrl)) {
            this.footerIconUrl = footerIconUrl;
        } else if (!footerIconUrl.isEmpty()) {
            throw new IllegalArgumentException("Invalid footer icon URL");
        }
    }
}
