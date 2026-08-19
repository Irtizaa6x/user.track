package main.java;

import javax.swing.*;

/**
 * Main class - Entry point of the User Session Tracker application
 * Demonstrates:
 * - Application entry point
 * - GUI look and feel setup
 * - Launching the login screen
 */
public class Main {
    
    /**
     * Main method - Application entry point
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Set look and feel to system default for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback to default look and feel
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // Ignore - use default
            }
        }
        
        // Launch the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create and show the login frame
                    new LoginFrame();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, 
                        "Error starting application: " + e.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}
