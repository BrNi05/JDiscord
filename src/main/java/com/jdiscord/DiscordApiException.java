package com.jdiscord;

/**
 * Exception representing an (HTTP) error response from the Discord API.
 */
public class DiscordApiException extends Exception {
    private final int statusCode;

    /**
     * Constructor for DiscordApiException.
     * @param statusCode The HTTP status code returned by the Discord API.
     * @param message    The error message.
     */
    public DiscordApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Gets the error message with the status code included.
     * @return The formatted error message.
     */
    @Override
    public String getMessage() {
        return "Discord API error (" + statusCode + "): " + super.getMessage() + ".";
    }
}
