package com.jdiscord;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JPanel;

class InputFieldTest {

    @Test
    void initialValueIsNull() {
        InputField field = new InputField("Placeholder");
        assertNull(field.getValue());
    }

    @Test
    void setValueAndGetValue() {
        InputField field = new InputField("Placeholder");
        field.setValue("Hello");
        assertEquals("Hello", field.getValue());
    }

    @Test
    void setValueToEmptyReturnsNull() {
        InputField field = new InputField("Placeholder");
        field.setValue("");
        assertNull(field.getValue());
    }

    @Test
    void getPanelNotNull() {
        InputField field = new InputField("Placeholder");
        JPanel panel = field.getPanel();
        assertNotNull(panel);
    }
}
