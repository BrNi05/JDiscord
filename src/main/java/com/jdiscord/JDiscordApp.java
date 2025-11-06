package com.jdiscord;

import java.util.List;

import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Color;

import org.jdesktop.swingx.prompt.PromptSupport;

/**
 * Main application class for JDiscord.
 * Provides a GUI for sending messages and files to Discord webhooks.
 */
public class JDiscordApp {
    private JFrame frame;

    // Color picker result
    private static final String DEFAULT_COLOR = "5132370"; // Flatlaf grey for button
    private String pickedColor = DEFAULT_COLOR;

    // Consts
    private static final String NO_ASSIGNED_FIELDS = "No assigned fields";
    private static final String NO_PROFILE = "No saved profiles";

    /**
     * Constructor to set up the GUI components and event listeners.
     */
    public JDiscordApp() {
        frame = new JFrame("JDiscord");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Menu bar
        JPanel menuPanel = new JPanel(new BorderLayout());
        JPanel leftMenu = new JPanel();
        JPanel rightMenu = new JPanel();
        menuPanel.add(leftMenu, BorderLayout.WEST);
        menuPanel.add(rightMenu, BorderLayout.EAST);

        // Menu bar: left side
        leftMenu.setLayout(new FlowLayout(FlowLayout.LEFT));

        JTextField profileField = new JTextField(12);
        PromptSupport.setPrompt("Profile name", profileField);
        JButton saveProfileButton = new JButton("Save Profile");

        JComboBox<String> profileDropdown = new JComboBox<>(new String[]{NO_PROFILE});
        populateProfilesDropdown(profileDropdown);
        profileDropdown.setSelectedIndex(0);

        JButton deleteProfileButton = new JButton("Delete Profile");

        leftMenu.add(profileField);
        leftMenu.add(saveProfileButton);
        leftMenu.add(profileDropdown);
        leftMenu.add(deleteProfileButton);

        // Menu bar: right side
        rightMenu.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton sendButton = new JButton("SEND");

        rightMenu.add(sendButton);

        // Windows layout
        frame.setLayout(new BorderLayout());
        frame.add(menuPanel, BorderLayout.NORTH);

        // Main UI
        JPanel mainCenter = new JPanel(new GridLayout(1, 2));

        // Main UI: left panel
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Message Basics"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        InputField usernameInput = new InputField("Username");
        leftPanel.add(usernameInput.getPanel(), row(gbc, 0));

        InputField msgInput = new InputField("Message Text");
        leftPanel.add(msgInput.getPanel(), row(gbc, 1));

        InputField descInput = new InputField("Description");
        leftPanel.add(descInput.getPanel(), row(gbc, 2));

        JButton colorPicker = new JButton("Message Color");
        leftPanel.add(colorPicker, row(gbc, 3));

        JPanel titleGroup = new JPanel(new GridLayout(2, 1, 5, 5));
        titleGroup.setBorder(BorderFactory.createTitledBorder("Title"));

        InputField titleInput = new InputField("Title Text");
        titleGroup.add(titleInput.getPanel());
        
        InputField titleUrlInput = new InputField("Title URL");
        titleGroup.add(titleUrlInput.getPanel());
        
        leftPanel.add(titleGroup, row(gbc, 4));

        InputField avatarIconUrlInput = new InputField("Avatar URL");
        leftPanel.add(avatarIconUrlInput.getPanel(), row(gbc, 5));

        // Main UI: right panel
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Embed Extras"));
        GridBagConstraints gbcR = new GridBagConstraints();
        gbcR.insets = new Insets(5, 5, 5, 5);
        gbcR.fill = GridBagConstraints.HORIZONTAL;
        gbcR.weightx = 1.0;
        gbcR.gridx = 0;

        InputField imageUrlInput = new InputField("Image URL");
        rightPanel.add(imageUrlInput.getPanel(), row(gbcR, 0));

        JPanel dropdownPanel = new JPanel(new BorderLayout(5, 0));
        JComboBox<String> dropdown = new JComboBox<>(new String[]{NO_ASSIGNED_FIELDS});
        JButton deleteBtn = new JButton("Delete");
        dropdownPanel.add(dropdown, BorderLayout.CENTER);
        dropdownPanel.add(deleteBtn, BorderLayout.EAST);
        rightPanel.add(dropdownPanel, row(gbcR, 1));

        JPanel twoInputsPanel = new JPanel(new BorderLayout(5, 0));
        JPanel fieldsPanel = new JPanel(new GridLayout(1, 2, 5, 0));

        JTextField keyInput = new JTextField(10);
        PromptSupport.setPrompt("Key", keyInput);
        fieldsPanel.add(keyInput);

        JTextField valueInput = new JTextField(10);
        PromptSupport.setPrompt("Value", valueInput);
        fieldsPanel.add(valueInput);

        JButton addBtn = new JButton("Add");
        twoInputsPanel.add(fieldsPanel, BorderLayout.CENTER);
        twoInputsPanel.add(addBtn, BorderLayout.EAST);
        rightPanel.add(twoInputsPanel, row(gbcR, 2));

        JPanel authorGroup = new JPanel(new GridLayout(3, 1, 5, 5));
        authorGroup.setBorder(BorderFactory.createTitledBorder("Author"));

        InputField authorInput = new InputField("Author Name");
        authorGroup.add(authorInput.getPanel());

        InputField authorUrlInput = new InputField("Author URL");
        authorGroup.add(authorUrlInput.getPanel());

        InputField authorIconInput = new InputField("Author Icon URL");
        authorGroup.add(authorIconInput.getPanel());
    
        rightPanel.add(authorGroup, row(gbcR, 3));

        InputField filePathInput = new InputField("File to send (max 8 MB)");
        JButton browseButton = new JButton("Browse");
        JPanel filePanel = new JPanel(new BorderLayout(5, 0));
        filePanel.add(filePathInput.getPanel(), BorderLayout.CENTER);
        filePanel.add(browseButton, BorderLayout.EAST);
        rightPanel.add(filePanel, row(gbcR, 4));

        // Split the main UI
        mainCenter.add(new JScrollPane(leftPanel));
        mainCenter.add(new JScrollPane(rightPanel));
        frame.add(mainCenter, BorderLayout.CENTER);

        // Main UI: bottom panel
        JPanel southPanel = new JPanel(new GridBagLayout());
        southPanel.setBorder(BorderFactory.createTitledBorder("Webhook & Footer"));
        GridBagConstraints gbcBottom = new GridBagConstraints();
        gbcBottom.insets = new Insets(5, 5, 5, 5);
        gbcBottom.fill = GridBagConstraints.HORIZONTAL;
        gbcBottom.weightx = 1.0;
        gbcBottom.gridx = 0;

        InputField webhookInput = new InputField("Webhook URL");
        southPanel.add(webhookInput.getPanel(), row(gbcBottom, 0));

        InputField footerTextInput = new InputField("Footer Text");
        southPanel.add(footerTextInput.getPanel(), row(gbcBottom, 1));

        InputField footerIconInput = new InputField("Footer Icon URL");
        southPanel.add(footerIconInput.getPanel(), row(gbcBottom, 2));

        JCheckBox timestampCheckbox = new JCheckBox("Include Timestamp");
        southPanel.add(timestampCheckbox, row(gbcBottom, 3));

        frame.add(southPanel, BorderLayout.SOUTH);

        // Color picker button event listener
        colorPicker.addActionListener(event -> {
            Color initialColor = Color.WHITE;
            Color color = JColorChooser.showDialog(frame, "Select a color", initialColor);
            if (color != null) {
                pickedColor = Integer.toString(color.getRGB() & 0xFFFFFF); // decimal
                colorPicker.setBackground(color);
            }
        });

        // File browser button event listener
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a file to send");
            
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathInput.setValue(selectedFile.getAbsolutePath());
            }
        });

        // Send button event listener
        // Errors handled in sendMessage and sendFileMessage methods
        sendButton.addActionListener(event -> {
            if (filePathInput.getValue() == null) {
                sendMessage(
                    webhookInput.getValue() != null ? webhookInput.getValue() : ProfileManager.getWebhook(),
                    usernameInput,
                    avatarIconUrlInput,
                    msgInput,
                    titleInput,
                    descInput,
                    pickedColor,
                    titleUrlInput,
                    authorInput,
                    authorUrlInput,
                    authorIconInput,
                    imageUrlInput,
                    footerTextInput,
                    footerIconInput,
                    timestampCheckbox,
                    dropdown
                );
            } else {
                sendFileMessage(
                    webhookInput.getValue() != null ? webhookInput.getValue() : ProfileManager.getWebhook(),
                    usernameInput,
                    msgInput,
                    filePathInput
                );
            }
        });

        // Dropdown delete button event listener
        deleteBtn.addActionListener(event -> {
            ComboBoxModel<String> model = dropdown.getModel();
            if (model.getSize() == 1 && model.getElementAt(0).equals(NO_ASSIGNED_FIELDS)) return;

            int selectedIndex = dropdown.getSelectedIndex();
            if (selectedIndex != -1) {
                DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<>();
                for (int i = 0; i < model.getSize(); i++) {
                    if (i != selectedIndex) {
                        newModel.addElement(model.getElementAt(i));
                    }
                }
                if (newModel.getSize() == 0) {
                    newModel.addElement(NO_ASSIGNED_FIELDS);
                }
                dropdown.setModel(newModel);
            }
        });

        // Dropdown add button event listener
        addBtn.addActionListener(event -> {
            String key = keyInput.getText().trim();
            String value = valueInput.getText().trim();
            if (key.isEmpty() || value.isEmpty()) {
                ErrorDialog.showError(frame, "Provide a key and a value.");
                return;
            }

            ComboBoxModel<String> model = dropdown.getModel();

            // Max. 25 fields
            if (model.getSize() >= 25) {
                ErrorDialog.showError(frame, "Max. number of fields (25) reached.");
                return;
            }

            // Construct new model
            DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<>();
            for (int i = 0; i < model.getSize(); i++) {
                if (!model.getElementAt(i).equals(NO_ASSIGNED_FIELDS)) {
                    newModel.addElement(model.getElementAt(i));
                }
            }

            newModel.addElement(key + ":" + value);
            dropdown.setModel(newModel);

            keyInput.setText("");
            valueInput.setText("");
        });

        // Save profile button event listener
        saveProfileButton.addActionListener(event -> {
            // Set the profile input field
            profileField.setText(
                profileField.getText().trim().isEmpty() ? "default" : profileField.getText().trim()
            );

            try {
                ProfileManager.saveProfile(
                    profileField.getText(),
                    webhookInput.getValue() == null ? "" : webhookInput.getValue(),
                    usernameInput,
                    avatarIconUrlInput,
                    msgInput,
                    titleInput,
                    descInput,
                    pickedColor,
                    titleUrlInput,
                    authorInput,
                    authorUrlInput,
                    authorIconInput,
                    imageUrlInput,
                    footerTextInput,
                    footerIconInput,
                    timestampCheckbox,
                    dropdown,
                    filePathInput
                );
            } catch (IOException e) {
                ErrorDialog.showError(null, "Failed to save profile: " + profileField.getText());
                return;
            }

            // Repopulate profiles dropdown
            populateProfilesDropdown(profileDropdown);

            // Select the saved profile (and reload it)
            profileDropdown.setSelectedItem(profileField.getText());
        });

        // Load profile dropdown event listener
        profileDropdown.addActionListener(event -> {
            // Do not load if there are no profiles
            if (profileDropdown.getSelectedItem().equals(NO_PROFILE)) return;

            try {
                pickedColor = ProfileManager.loadProfile(
                    (String) profileDropdown.getSelectedItem(),
                    usernameInput,
                    avatarIconUrlInput,
                    msgInput,
                    titleInput,
                    descInput,
                    titleUrlInput,
                    authorInput,
                    authorUrlInput,
                    authorIconInput,
                    imageUrlInput,
                    footerTextInput,
                    footerIconInput,
                    timestampCheckbox,
                    dropdown,
                    filePathInput
                );
            } catch (IOException e) {
                ErrorDialog.showError(null, "Failed to load profile: " + profileDropdown.getSelectedItem());
                return;
            }

            if (pickedColor == null) pickedColor = DEFAULT_COLOR;
            colorPicker.setBackground(new Color(Integer.parseInt(pickedColor)));

            webhookInput.setValue(ProfileManager.getWebhook());
            profileField.setText((String) profileDropdown.getSelectedItem());
        });

        // Delete profile button event listener
        deleteProfileButton.addActionListener(event -> {
            // Do not delete if there are no profiles
            if (profileDropdown.getSelectedItem().equals(NO_PROFILE)) return;

            try {
                ProfileManager.deleteProfile((String) profileDropdown.getSelectedItem());
            } catch (IOException e) {
                ErrorDialog.showError(null, e.getMessage());
                return;
            }

            populateProfilesDropdown(profileDropdown);
            
            // Set the profile input field
            if (!((String) profileDropdown.getSelectedItem()).equals(NO_PROFILE)) {
                profileDropdown.setSelectedIndex(0);
                profileField.setText((String) profileDropdown.getSelectedItem());
            } else {
                profileField.setText("");
            }
        });

        frame.setVisible(true);
    }

    /**
     * Populate the profiles dropdown with saved profiles.
     * If no profiles are saved, display "No saved profiles".
     * @param profileDropdown The JComboBox to populate.
     */
    private void populateProfilesDropdown(JComboBox<String> profileDropdown) {
        List<String> profiles = ProfileManager.listProfiles();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        if (!profiles.isEmpty()) {
            for (String profile : profiles) {
                model.addElement(profile);
            }
        } else {
            model.addElement(NO_PROFILE);
        }

        profileDropdown.setModel(model);
    }

    /**
     * Send button event listener: send message
     * @param webhook            The webhook URL to send the message to.
     * @param usernameInput      The input field for the username.
     * @param avatarIconUrlInput The input field for the avatar icon URL.
     * @param msgInput           The input field for the message text.
     * @param titleInput         The input field for the title text.
     * @param descInput          The input field for the description.
     * @param pickedColor        The picked color in decimal string format.
     * @param titleUrlInput      The input field for the title URL.
     * @param authorInput        The input field for the author name.
     * @param authorUrlInput     The input field for the author URL.
     * @param authorIconInput    The input field for the author icon URL.
     * @param imageUrlInput      The input field for the image URL.
     * @param footerTextInput    The input field for the footer text.
     * @param footerIconInput    The input field for the footer icon URL.
     * @param timestampCheckbox  The checkbox for including a timestamp.
     * @param dropdown           The dropdown containing the fields to include in the embed.
     * Exceptions that are thrown deeper in the call stack are caught and handled here.
     */
    private void sendMessage(
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
        JComboBox <String> dropdown
    ) {
        try {
            // Build fields list from dropdown
            List<Field> fields = Field.parseDropdown(dropdown);

            WebhookSender sender = new WebhookSender(webhook);
            Message message = new Message(
                webhook,
                usernameInput.getValue(),
                avatarIconUrlInput.getValue(),
                msgInput.getValue(),
                titleInput.getValue(),
                descInput.getValue(),
                pickedColor,
                titleUrlInput.getValue(),
                authorInput.getValue(),
                authorUrlInput.getValue(),
                authorIconInput.getValue(),
                imageUrlInput.getValue(),
                fields,
                footerTextInput.getValue(),
                footerIconInput.getValue(),
                timestampCheckbox.isSelected()
            );
            sender.sendMessage(message);
        } catch (IllegalArgumentException | DiscordApiException e) {
            ErrorDialog.showError(null, e.getMessage());
        } catch (IOException e) {
            ErrorDialog.showError(null, "Failed to send message - network error.");
        }
    }

    /**
     * Send button event listener: send file
     * @param webhook       The webhook URL to send the file to.
     * @param usernameInput The input field for the username.
     * @param texInputField The input field for the message text.
     * @param filePathInput The input field for the file path.
     * Exceptions that are thrown deeper in the call stack are caught and handled here.
     */
    private void sendFileMessage(
        String webhook,
        InputField usernameInput,
        InputField texInputField,
        InputField filePathInput
    ) {
        try {
            WebhookSender sender = new WebhookSender(webhook);
            FileMessage fileMessage = new FileMessage(
                webhook,
                usernameInput.getValue(),
                texInputField.getValue(),
                filePathInput.getValue()
            );
            sender.sendFile(fileMessage);
        } catch (IllegalArgumentException | DiscordApiException e) {
            ErrorDialog.showError(null, e.getMessage());
        } catch (IOException e) {
            ErrorDialog.showError(null, "Failed to send file: " + e.getMessage());
        }
    }

    /**
     * Helper method to create a copy of GridBagConstraints with modified gridy.
     * @param gbc The original GridBagConstraints object.
     * @param y   The new gridy value.
     * @return A new GridBagConstraints object with the updated gridy.
     */
    private GridBagConstraints row(GridBagConstraints gbc, int y) {
        GridBagConstraints copy = (GridBagConstraints) gbc.clone();
        copy.gridy = y;
        return copy;
    }
}
