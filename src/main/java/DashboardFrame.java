package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DashboardFrame - Main dashboard GUI after successful login
 * Demonstrates:
 * - Multiple GUI components
 * - Event handling
 * - Navigation between frames
 */
public class DashboardFrame extends JFrame {
    // GUI Components
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel statsPanel;
    private JPanel buttonPanel;
    
    private JLabel welcomeLabel;
    private JLabel userInfoLabel;
    private JLabel totalUsersLabel;
    private JLabel activeSessionsLabel;
    private JLabel regularUsersLabel;
    
    private JButton addUserButton;
    private JButton removeUserButton;
    private JButton viewSessionsButton;
    private JButton logoutButton;
    private JButton refreshButton;
    
    // Application references
    private SessionManager sessionManager;
    private User currentUser;
    
    /**
     * Constructor - Sets up the dashboard
     * @param sessionManager Reference to session manager
     * @param currentUser The logged-in user
     */
    public DashboardFrame(SessionManager sessionManager, User currentUser) {
        this.sessionManager = sessionManager;
        this.currentUser = currentUser;
        
        // Setup frame
        setTitle("User Session Tracker - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize components
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        updateStats();
        
        setVisible(true);
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Panels
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(236, 240, 241));
        
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(236, 240, 241));
        
        statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setBackground(new Color(236, 240, 241));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        buttonPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Header labels
        welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        
        String roleInfo = currentUser.getRole().equals("ADMIN") ? "Administrator" : "User";
        userInfoLabel = new JLabel("Role: " + roleInfo + " | User Session Tracker");
        userInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userInfoLabel.setForeground(new Color(189, 195, 199));
        
        // Stats labels
        totalUsersLabel = createStatLabel("👥 Total Users", "0");
        activeSessionsLabel = createStatLabel("🟢 Active Sessions", "0");
        regularUsersLabel = createStatLabel("📝 Regular Users", "0");
        
        // Buttons
        addUserButton = createStyledButton("➕ Add User", new Color(46, 204, 113));
        removeUserButton = createStyledButton("❌ Remove User", new Color(231, 76, 60));
        viewSessionsButton = createStyledButton("📋 View Sessions", new Color(52, 152, 219));
        logoutButton = createStyledButton("🚪 Logout", new Color(149, 165, 166));
        refreshButton = new JButton("🔄 Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        refreshButton.setBackground(new Color(52, 73, 94));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
    }
    
    /**
     * Helper method to create a stat label
     */
    private JLabel createStatLabel(String title, String value) {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" +
                                  title + "<br><span style='font-size: 24px; font-weight: bold;'>" + 
                                  value + "</span></div></html>", JLabel.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        return label;
    }
    
    /**
     * Helper method to create a styled button
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        return button;
    }
    
    /**
     * Set up the layout
     */
    private void setupLayout() {
        // Header Panel
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(userInfoLabel, BorderLayout.EAST);
        
        // Stats Panel
        statsPanel.add(totalUsersLabel);
        statsPanel.add(activeSessionsLabel);
        statsPanel.add(regularUsersLabel);
        
        // Button Panel
        buttonPanel.add(addUserButton);
        buttonPanel.add(removeUserButton);
        buttonPanel.add(viewSessionsButton);
        buttonPanel.add(logoutButton);
        
        // Content Panel
        contentPanel.add(statsPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Main Panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    /**
     * Set up event handlers
     */
    private void setupEventHandlers() {
        // Add User
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserFrame(sessionManager, DashboardFrame.this);
            }
        });
        
        // Remove User
        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveUser();
            }
        });
        
        // View Sessions
        viewSessionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SessionTableFrame(sessionManager);
            }
        });
        
        // Logout
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
        
        // Refresh
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStats();
            }
        });
    }
    
    /**
     * Update statistics on the dashboard
     */
    public void updateStats() {
        int totalUsers = sessionManager.getTotalUsers();
        int activeSessions = sessionManager.getActiveSessionCount();
        int regularUsers = sessionManager.getRegularUserCount();
        
        totalUsersLabel.setText("<html><div style='text-align: center;'>👥 Total Users<br>" +
                               "<span style='font-size: 24px; font-weight: bold;'>" + totalUsers + 
                               "</span></div></html>");
        activeSessionsLabel.setText("<html><div style='text-align: center;'>🟢 Active Sessions<br>" +
                                   "<span style='font-size: 24px; font-weight: bold;'>" + activeSessions + 
                                   "</span></div></html>");
        regularUsersLabel.setText("<html><div style='text-align: center;'>📝 Regular Users<br>" +
                                 "<span style='font-size: 24px; font-weight: bold;'>" + regularUsers + 
                                 "</span></div></html>");
    }
    
    /**
     * Handle remove user functionality
     */
    private void handleRemoveUser() {
        // Get list of regular users
        java.util.ArrayList<User> regularUsers = sessionManager.getRegularUsers();
        
        if (regularUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No regular users to remove.", 
                "Remove User", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create array of usernames
        String[] usernames = new String[regularUsers.size()];
        for (int i = 0; i < regularUsers.size(); i++) {
            usernames[i] = regularUsers.get(i).getUsername();
        }
        
        // Show selection dialog
        String selectedUsername = (String) JOptionPane.showInputDialog(
            this,
            "Select user to remove:",
            "Remove User",
            JOptionPane.QUESTION_MESSAGE,
            null,
            usernames,
            usernames[0]
        );
        
        if (selectedUsername != null && !selectedUsername.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove user '" + selectedUsername + "'?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // First logout the user if logged in
                    sessionManager.logoutUser(selectedUsername);
                    // Then remove the user
                    sessionManager.removeUser(selectedUsername);
                    
                    JOptionPane.showMessageDialog(this, 
                        "User '" + selectedUsername + "' removed successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    updateStats();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, 
                        ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Handle logout
     */
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            // End the user's session
            sessionManager.logoutUser(currentUser.getUsername());
            
            // Close dashboard and open login
            this.dispose();
            new LoginFrame();
        }
    }
}
