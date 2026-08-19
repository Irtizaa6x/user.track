package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AddUserFrame - Dialog for adding new users
 * Demonstrates:
 * - JDialog usage
 * - Input validation
 * - Exception handling
 * - Event handling
 */
public class AddUserFrame extends JFrame {
    // GUI Components
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JButton addButton;
    private JButton cancelButton;
    private JLabel statusLabel;
    private JComboBox<String> roleComboBox;
    
    // Application references
    private SessionManager sessionManager;
    private DashboardFrame parentDashboard;
    
    /**
     * Constructor
     * @param sessionManager Reference to session manager
     * @param parentDashboard Reference to parent dashboard for refreshing
     */
    public AddUserFrame(SessionManager sessionManager, DashboardFrame parentDashboard) {
        this.sessionManager = sessionManager;
        this.parentDashboard = parentDashboard;
        
        // Setup frame as dialog
        setTitle("Add New User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 320);
        setLocationRelativeTo(parentDashboard);
        setResizable(false);
        
        // Initialize components
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        setVisible(true);
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 248, 248));
        
        // Title
        titleLabel = new JLabel("➕ Add New User", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(248, 248, 248));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        formPanel.add(usernameField, gbc);
        
        // Password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        formPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setEchoChar('•');
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        formPanel.add(passwordField, gbc);
        
        // Confirm Password
        confirmLabel = new JLabel("Confirm:");
        confirmLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        formPanel.add(confirmLabel, gbc);
        
        confirmField = new JPasswordField(20);
        confirmField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmField.setEchoChar('•');
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.8;
        formPanel.add(confirmField, gbc);
        
        // Role
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        formPanel.add(roleLabel, gbc);
        
        String[] roles = {"USER", "ADMIN"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.8;
        formPanel.add(roleComboBox, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(248, 248, 248));
        
        addButton = new JButton("Add User");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(100, 35));
        
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(100, 35));
        
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        // Status label
        statusLabel = new JLabel("Enter user details", JLabel.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(100, 100, 100));
        
        // Add to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    /**
     * Set up event handlers
     */
    private void setupEventHandlers() {
        // Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddUser();
            }
        });
        
        // Cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Enter key on confirm field
        confirmField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddUser();
            }
        });
    }
    
    /**
     * Handle add user operation
     */
    private void handleAddUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        
        // Validate input
        if (username.isEmpty()) {
            showStatus("⚠️ Username cannot be empty", new Color(231, 76, 60));
            return;
        }
        
        if (username.length() < 3) {
            showStatus("⚠️ Username must be at least 3 characters", new Color(231, 76, 60));
            return;
        }
        
        if (password.isEmpty()) {
            showStatus("⚠️ Password cannot be empty", new Color(231, 76, 60));
            return;
        }
        
        if (password.length() < 4) {
            showStatus("⚠️ Password must be at least 4 characters", new Color(231, 76, 60));
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showStatus("⚠️ Passwords do not match", new Color(231, 76, 60));
            passwordField.setText("");
            confirmField.setText("");
            passwordField.requestFocus();
            return;
        }
        
        // Try to add user
        try {
            sessionManager.addUser(username, password, role);
            
            // Success
            showStatus("✅ User '" + username + "' added successfully!", new Color(46, 204, 113));
            clearFields();
            
            // Refresh dashboard stats
            if (parentDashboard != null) {
                parentDashboard.updateStats();
            }
            
            // Optional: Close after success
            JOptionPane.showMessageDialog(this, 
                "User '" + username + "' added successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (IllegalArgumentException ex) {
            showStatus("❌ " + ex.getMessage(), new Color(231, 76, 60));
        }
    }
    
    /**
     * Helper method to show status
     */
    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }
    
    /**
     * Clear all input fields
     */
    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmField.setText("");
        roleComboBox.setSelectedIndex(0);
        usernameField.requestFocus();
    }
}
