package com.jdiscord;

import java.util.List;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * Field class to represent a name-value pair for Discord embed fields.
 */
public class Field {
    private String key = null;
    private String value = null;

    /**
     * Constructor for Field.
     * @param key  The key of the field (max 256 characters).
     * @param value The value of the field (max 1024 characters).
     * @throws IllegalArgumentException if name or value exceed their character limits.
     */
    public Field(String key, String value) throws IllegalArgumentException {
        if (key.length() > 256) {
            throw new IllegalArgumentException("Field name must be 256 characters or less.");
        }
        if (value.length() > 1024) {
            throw new IllegalArgumentException("Field value must be 1024 characters or less.");
        }
        this.key = key;
        this.value = value;
    }

    /**
     * Get the JPanel containing the input field.
     * @return The JPanel with the input field.
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the text value of the input field.
     * @return The text in the input field.
     */
    public String getValue() {
        return value;
    }

    /**
     * Parse a JComboBox dropdown to extract Field objects.
     * @param dropdown The JComboBox containing field entries in "key:value" format.
     * @return A list of Field objects, or null if no valid fields are present.
     */
    public static List<Field> parseDropdown(JComboBox <String> dropdown) {
        ComboBoxModel<String> model = dropdown.getModel();
        List<Field> fields = new ArrayList<Field>();

        if (model.getSize() == 1 && model.getElementAt(0).equals("No assigned fields")) return null;

        for (int i = 0; i < model.getSize(); i++) {
            String item = model.getElementAt(i);
            String[] parts = item.split(":");

            fields.add(new Field(parts[0], parts[1]));
        }

        return fields;
    }
}
