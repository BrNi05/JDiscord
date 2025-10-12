package com.jdiscord;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.MalformedURLException;

import java.nio.file.Files;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A class for sending messages and files using a Discord webhook.
 */
public class WebhookSender {
    // The webhook URL
    private URL webhookUrl;

    // OkHttpClient instance for file uploads
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Constructor for WebhookSender.
     * @param webhookUrl The Discord webhook URL.
     * @throws IllegalArgumentException if the webhook URL is invalid.
     */
    public WebhookSender(String webhookUrl) throws IllegalArgumentException {
        try {
            this.webhookUrl = new URI(webhookUrl).toURL();
            if (!URLChecker.isWebhookUrl(webhookUrl)) throw new IllegalArgumentException();
        } catch (MalformedURLException | URISyntaxException | NullPointerException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid webhook URL");
        }
    }

    /**
     * Get the current timestamp in ISO 8601 format.
     * @return The current timestamp as a string.
     */
    private static String getTimestamp() {
        return DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }

    /**
     * Send a message using the webhook.
     * @param message The message to send.
     */
    public void sendMessage(Message message) {
        String payload = buildPayload(message);
        try {
            postMessage(payload);
        } catch (IOException e) {
            ErrorDialog.showError(null, "Failed to send message");
        }
    }

    /**
     * Send a file using the webhook.
     * @param fileMessage The file message to send.
     */
    public void sendFile(FileMessage fileMessage) {
        String payload = buildFilePayload(fileMessage);
        File targetFile = new File(fileMessage.getFilepath());
        postFile(targetFile, payload);
    }

    /**
     * Build JSON payload for Message
     * @param msg The message object.
     * @return The JSON payload as a string.
     */
    private String buildPayload(Message msg) {
        Gson gson = new Gson();
        JsonObject payload = new JsonObject();

        // Basic properties
        if (msg.getText() != null) payload.addProperty("content", msg.getText());
        if (msg.getUsername() != null) payload.addProperty("username", msg.getUsername());
        if (msg.getAvatarIconUrl() != null) payload.addProperty("avatar_url", msg.getAvatarIconUrl());

        JsonObject embed = new JsonObject();

        // Basic embeds
        if (msg.getTitle() != null) embed.addProperty("title", msg.getTitle());
        if (msg.getDescription() != null) embed.addProperty("description", msg.getDescription());
        if (msg.getColor() != null) embed.addProperty("color", Integer.decode(msg.getColor()));
        if (msg.getTitleUrl() != null) embed.addProperty("url", msg.getTitleUrl());

        // Author
        if (msg.getAuthor() != null) {
            JsonObject author = new JsonObject();
            author.addProperty("name", msg.getAuthor());
            if (msg.getAuthorUrl() != null) author.addProperty("url", msg.getAuthorUrl());
            if (msg.getAuthorIconUrl() != null) author.addProperty("icon_url", msg.getAuthorIconUrl());
            embed.add("author", author);
        }

        // Image
        if (msg.getImageUrl() != null) {
            JsonObject image = new JsonObject();
            image.addProperty("url", msg.getImageUrl());
            embed.add("image", image);
        }

        // Footer
        if (msg.getFooterText() != null) {
            JsonObject footer = new JsonObject();
            footer.addProperty("text", msg.getFooterText());
            if (msg.getFooterIconUrl() != null) footer.addProperty("icon_url", msg.getFooterIconUrl());
            embed.add("footer", footer);
        }

        // Fields
        List<Field> fields = msg.getFields();
        if (fields.isEmpty()) {
            JsonArray fieldsArray = new JsonArray();
            for (Field f : fields) {
                JsonObject field = new JsonObject();
                field.addProperty("name", f.getKey());
                field.addProperty("value", f.getValue());
                field.addProperty("inline", true); // Cannot be set from GUI
                fieldsArray.add(field);
            }
            embed.add("fields", fieldsArray);
        }

        // Timestamp
        if (msg.getTimestamp()) embed.addProperty("timestamp", getTimestamp());

        // Add embed to payload
        JsonArray embeds = new JsonArray();
        embeds.add(embed);
        payload.add("embeds", embeds);

        return gson.toJson(payload);
    }

    /**
     * Build JSON payload for FileMessage
     * @param fileMessage The file message object.
     * @return The JSON payload as a string.
     */
    private String buildFilePayload(FileMessage fileMessage) {
        Gson gson = new Gson();
        JsonObject payload = new JsonObject();

        if (fileMessage.getText() != null) {
            payload.addProperty("content", fileMessage.getText());
        }

        if (fileMessage.getUsername() != null) {
            payload.addProperty("username", fileMessage.getUsername());
        }

        return gson.toJson(payload);
    }

    /**
     * POST JSON payload to the webhook URL
     * @param json The JSON payload as a string.
     * @throws IOException if an I/O error occurs.
     */
    private void postMessage(String json) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) webhookUrl.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        int code = conn.getResponseCode();
        if (400 <= code && code < 600) ErrorDialog.showError(null, "JDiscord: Discord API error: " + code);
    }

    /**
     * POST a file using OkHttp3
     * @param file The file to upload.
     * @param payload The JSON payload as a string.
     */
    public void postFile(File file, String payload) {
        MediaType fileType = MediaType.parse("application/octet-stream");
        try {
            MediaType.parse(Files.probeContentType(file.toPath()));
        } catch (IOException e) {
            /* Swallow the error */
        }

        // Request body
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("payload_json", null,
                        RequestBody.create(payload, MediaType.parse("application/json; charset=utf-8")))
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(file, fileType))
                .build();

        // Request
        Request request = new Request.Builder()
            .url(webhookUrl)
            .post(body)
            .build();

        // Execute
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(String.valueOf(response.code()));
        } catch (IOException e) {
            ErrorDialog.showError(null, "JDiscord: Discord API error: " + e.getMessage());
        }
    }
}
