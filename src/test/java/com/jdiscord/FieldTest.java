package com.jdiscord;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JComboBox;

import java.util.List;

class FieldTest {

    // Constructor tests

    @Test
    void validField() {
        Field f = new Field("key", "value");
        assertEquals("key", f.getKey());
        assertEquals("value", f.getValue());
    }

    @Test
    void keyTooLongThrows() {
        String longKey = "a".repeat(257);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Field(longKey, "value"));
        assertEquals("Field name must be 256 characters or less.", ex.getMessage());
    }

    @Test
    void valueTooLongThrows() {
        String longValue = "a".repeat(1025);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Field("key", longValue));
        assertEquals("Field value must be 1024 characters or less.", ex.getMessage());
    }

    // parseDropdown() tests

    @Test
    void parseDropdownWithNoFieldsReturnsEmptyList() {
        JComboBox<String> dropdown = new JComboBox<>(new String[]{"No assigned fields"});
        List<Field> fields = Field.parseDropdown(dropdown);
        assertTrue(fields.isEmpty());
    }

    @Test
    void parseDropdownWithValidItems() {
        JComboBox<String> dropdown = new JComboBox<>(new String[]{"key1:value1", "key2:value2"});
        List<Field> fields = Field.parseDropdown(dropdown);

        assertEquals(2, fields.size());
        assertEquals("key1", fields.get(0).getKey());
        assertEquals("value1", fields.get(0).getValue());
        assertEquals("key2", fields.get(1).getKey());
        assertEquals("value2", fields.get(1).getValue());
    }
}
