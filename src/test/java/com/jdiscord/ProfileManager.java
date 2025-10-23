package com.jdiscord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.nio.file.Path;
import java.util.List;

class ProfileManagerTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    void setup() {
        ProfileManager.setSaveDir(tempDir.toFile().getAbsolutePath());
    }

    // Combined test case (full workflow)

    @Test
    void saveLoadDeleteProfileWorks() {
        InputField username = new InputField(""), msg = new InputField(""), avatar = new InputField("");
        InputField title = new InputField(""), desc = new InputField(""), titleUrl = new InputField("");
        InputField author = new InputField(""), authorUrl = new InputField(""), authorIcon = new InputField("");
        InputField imageUrl = new InputField(""), footerText = new InputField(""), footerIcon = new InputField("");
        InputField filePath = new InputField("");
        JCheckBox timestamp = new JCheckBox();
        JComboBox<String> dropdown = new JComboBox<>();

        username.setValue("user1");
        msg.setValue("Hello");
        timestamp.setSelected(true);
        dropdown.addItem("field1");
        filePath.setValue("/tmp/file.txt");

        // Save profile
        ProfileManager.saveProfile("p1", "https://discord.com/api/webhooks/abc123",
            username, avatar, msg, title, desc, "123", titleUrl, author, authorUrl,
            authorIcon, imageUrl, footerText, footerIcon, timestamp, dropdown, filePath);

        // List profiles
        List<String> profiles = ProfileManager.listProfiles();
        assertTrue(profiles.contains("p1"));

        // Load profile
        InputField username2 = new InputField("");
        InputField msg2 = new InputField("");
        InputField avatar2 = new InputField("");
        JCheckBox timestamp2 = new JCheckBox();
        JComboBox<String> dropdown2 = new JComboBox<>();
        InputField filePath2 = new InputField("");

        String color = ProfileManager.loadProfile("p1",
            username2, avatar2, msg2, title, desc, titleUrl, author, authorUrl,
            authorIcon, imageUrl, footerText, footerIcon, timestamp2, dropdown2, filePath2);

        assertEquals("user1", username2.getValue());
        assertEquals("Hello", msg2.getValue());
        assertTrue(timestamp2.isSelected());
        assertEquals(1, dropdown2.getItemCount());
        assertEquals("field1", dropdown2.getItemAt(0));
        assertEquals("/tmp/file.txt", filePath2.getValue());
        assertEquals("123", color);

        ProfileManager.deleteProfile("p1");
        assertFalse(ProfileManager.listProfiles().contains("p1"));
    }

    @Test
    void setGetWebhook() {
        ProfileManager.setWebhook("https://discord.com/api/webhooks/abc123");
        assertEquals("https://discord.com/api/webhooks/abc123", ProfileManager.getWebhook());
    }
}
