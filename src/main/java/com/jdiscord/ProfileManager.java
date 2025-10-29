package com.jdiscord;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;

import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * An aggregate class to hold profile data for serialization.
 * Effectively Message and FileMessage merged into one class.
 */
class ProfileData {
    String webhook;
    String username;
    String avatarIconUrl;
    String msg;
    String title;
    String desc;
    String pickedColor;
    String titleUrl;
    String author;
    String authorUrl;
    String authorIcon;
    String imageUrl;
    String footerText;
    String footerIcon;
    boolean timestamp;
    List<String> dropdownItems;
    String filePath;
}

/**
 * Profile manager for saving and loading profiles.
 */
public class ProfileManager {
    // Gson instance for JSON serialization/deserialization
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Save directory
    private static String saveDir = null;

    // Loaded webhook
    private static String webhook = null;

    // Consts
    private static final String JSON_EXT = ".json";

    /**
     * Initialize save directory.
     */
    static {
        saveDir = new File(System.getProperty("user.home"), "Documents/JDiscordProfiles").getAbsolutePath();

        File dir = new File(saveDir);
        if (!dir.exists()) dir.mkdirs();
    }

    /**
     * Private constructor to prevent instantiation
     */
    private ProfileManager() {}

    /**
     * Get the currently loaded webhook URL.
     * @return Webhook URL.
     */
    public static String getWebhook() {
        return webhook;
    }

    /**
     * Set the currently loaded webhook URL.
     * @param webhook Webhook URL.
     */
    public static void setWebhook(String webhook) {
        ProfileManager.webhook = webhook;
    }

    /**
     * Save profile data to a JSON file.
     * @param profileName        Profile name (without .json extension).
     * @param webhook            Webhook URL.
     * @param usernameInput      Username input field.
     * @param avatarIconUrlInput Avatar icon URL input field.
     * @param msgInput           Message input field.
     * @param titleInput         Title input field.
     * @param descInput          Description input field.
     * @param pickedColor        Picked color in decimal string format.
     * @param titleUrlInput      Title URL input field.
     * @param authorInput        Author name input field.
     * @param authorUrlInput     Author URL input field.
     * @param authorIconInput    Author icon URL input field.
     * @param imageUrlInput      Image URL input field.
     * @param footerTextInput    Footer text input field.
     * @param footerIconInput    Footer icon URL input field.
     * @param timestampCheckbox  Timestamp checkbox.
     * @param dropdown           Dropdown component for fields.
     * @param filePathInput      File path input field.
     * @throws IOException if an I/O error occurs during save.
     */
    public static void saveProfile(
        String profileName,
        String webhook,
        InputField usernameInput,
        InputField avatarIconUrlInput,
        InputField msgInput,
        InputField titleInput,
        InputField descInput,
        String pickedColor,
        InputField titleUrlInput,
        InputField authorInput,
        InputField authorUrlInput,
        InputField authorIconInput,
        InputField imageUrlInput,
        InputField footerTextInput,
        InputField footerIconInput,
        JCheckBox timestampCheckbox,
        JComboBox <String> dropdown,
        InputField filePathInput
    ) throws IOException {
        ProfileData data = new ProfileData();
        data.webhook = webhook;
        data.username = usernameInput.getValue();
        data.avatarIconUrl = avatarIconUrlInput.getValue();
        data.msg = msgInput.getValue();
        data.title = titleInput.getValue();
        data.desc = descInput.getValue();
        data.pickedColor = pickedColor;
        data.titleUrl = titleUrlInput.getValue();
        data.author = authorInput.getValue();
        data.authorUrl = authorUrlInput.getValue();
        data.authorIcon = authorIconInput.getValue();
        data.imageUrl = imageUrlInput.getValue();
        data.footerText = footerTextInput.getValue();
        data.footerIcon = footerIconInput.getValue();
        data.timestamp = timestampCheckbox.isSelected();
        data.dropdownItems = new java.util.ArrayList<>();
        ComboBoxModel<String> model = dropdown.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            data.dropdownItems.add(model.getElementAt(i));
        }
        data.filePath = filePathInput.getValue();

        try (FileWriter writer = new FileWriter(new File(saveDir, profileName + JSON_EXT))) {
            gson.toJson(data, writer);
        }
    }

    /**
     * Load profile data from a JSON file and populate input fields.
     * @param profileName        Profile name (without .json extension).
     * @param usernameInput      Username input field.
     * @param avatarIconUrlInput Avatar icon URL input field.
     * @param msgInput           Message input field.
     * @param titleInput         Title input field.
     * @param descInput          Description input field.
     * @param titleUrlInput      Title URL input field.
     * @param authorInput        Author name input field.
     * @param authorUrlInput     Author URL input field.
     * @param authorIconInput    Author icon URL input field.
     * @param imageUrlInput      Image URL input field.
     * @param footerTextInput    Footer text input field.
     * @param footerIconInput    Footer icon URL input field.
     * @param timestampCheckbox  Timestamp checkbox.
     * @param dropdown           Dropdown component for fields.
     * @param filePathInput      File path input field.
     * @return Picked color in decimal string format, or null if loading failed.
     * @throws IOException if an I/O error occurs during load.
     */
    public static String loadProfile(
        String profileName,
        InputField usernameInput,
        InputField avatarIconUrlInput,
        InputField msgInput,
        InputField titleInput,
        InputField descInput,
        InputField titleUrlInput,
        InputField authorInput,
        InputField authorUrlInput,
        InputField authorIconInput,
        InputField imageUrlInput,
        InputField footerTextInput,
        InputField footerIconInput,
        JCheckBox timestampCheckbox,
        JComboBox <String> dropdown,
        InputField filePathInput
    ) throws IOException {
        try (FileReader reader = new FileReader(new File(saveDir, profileName + JSON_EXT))) {
            ProfileData data = gson.fromJson(reader, ProfileData.class);

            webhook = data.webhook;
            usernameInput.setValue(data.username);
            avatarIconUrlInput.setValue(data.avatarIconUrl);
            msgInput.setValue(data.msg);
            titleInput.setValue(data.title);
            descInput.setValue(data.desc);
            titleUrlInput.setValue(data.titleUrl);
            authorInput.setValue(data.author);
            authorUrlInput.setValue(data.authorUrl);
            authorIconInput.setValue(data.authorIcon);
            imageUrlInput.setValue(data.imageUrl);
            footerTextInput.setValue(data.footerText);
            footerIconInput.setValue(data.footerIcon);
            timestampCheckbox.setSelected(data.timestamp);
            dropdown.removeAllItems();
            for (String item : data.dropdownItems) dropdown.addItem(item);
            filePathInput.setValue(data.filePath);

            return data.pickedColor;
        }
    }

    /**
     * List all saved profiles (without .json extension).
     * @return List of profile names.
     */
    public static List<String> listProfiles() {
        File dir = new File(saveDir);
        String[] files = dir.list((d, name) -> name.endsWith(JSON_EXT));
        return files != null ? List.of(files).stream().map(f -> f.substring(0, f.length() - 5)).toList() : List.of();
    }

    /**
     * Delete a saved profile.
     * @param profileName Profile name (without .json extension).
     * @throws IOException if an I/O error occurs during deletion.
     */
    public static void deleteProfile(String profileName) throws IOException {
        File file = new File(saveDir, profileName + JSON_EXT);
        if (file.exists()) {
            if (!file.delete()) throw new IOException("Failed to delete profile: " + profileName);
        } else throw new IOException("Profile not found: " + profileName);
    }

    /**
     * Set the save directory (for testing purposes).
     * @param dirPath Directory path.
     */
    public static void setSaveDir(String dirPath) {
        saveDir = dirPath;
    }
}
