package com.jdiscord;

import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;

/**
 * The main class for the JDiscord application.
 * Initializes the GUI with a dark theme.
 */
public class Main {
    public static void main(String[] args) {
        final Logger logger = Logger.getLogger("Initialization");

        try {
            // Shortcut for a nice looking GUI
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            logger.warning("JDiscord: Failed to initialize UI");
            System.exit(1);
        }

        // Create the GUI on the AWT Event Dispatch Thread
        SwingUtilities.invokeLater(JDiscordApp::new);
    }
}
