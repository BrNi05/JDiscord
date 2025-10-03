package com.jdiscord;

import javax.swing.*;
import java.awt.*;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 * A simple input field with a placeholder.
 * A JPanel is used for layout purposes.
 */
public class InputField {
    private JPanel panel;
    private JTextField inputField;

    /**
     * Constructor for InputField.
     * @param placeholder The placeholder text to display when the field is empty.
     */ 
    public InputField(String placeholder) {
        inputField = new JTextField(20);
        PromptSupport.setPrompt(placeholder, inputField);
        panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
    }

    /**
     * Get the JPanel containing the input field.
     * @return The JPanel with the input field.
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Get the text value of the input field.
     * @return The text in the input field, or null if empty.
     */
    public String getValue() {
        return inputField.getText().isEmpty() ? null : inputField.getText();
    }

    /**
     * Set the text value of the input field.
     * @param value The text to set in the input field.
     */
    public void setValue(String value) {
        inputField.setText(value);
    }
}
