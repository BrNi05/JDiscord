package com.jdiscord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * An error dialog override class for displaying error messages.
 */
public class ErrorDialog extends JDialog {
    /**
     * Private constructor to create an error dialog.
     * @param parent  The parent JFrame.
     * @param message The error message to display.
     */
    private ErrorDialog(JFrame parent,String message) {
        super(parent, "JDiscord Error", true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent e) -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        buttonPanel.setBackground(panel.getBackground());
        buttonPanel.add(okButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        pack();
        setMinimumSize(new Dimension(350, 150));
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    /**
     * Show an error dialog with the specified message.
     * @param parent  The parent JFrame.
     * @param message The error message to display.
     */
    public static void showError(JFrame parent, String message) {
        new ErrorDialog(parent, message).setVisible(true);
    }
}
