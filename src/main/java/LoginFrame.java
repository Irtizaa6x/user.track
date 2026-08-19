package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * LoginFrame - GUI for user login
 * Demonstrates:
 * - Swing GUI components
 * - Event handling with ActionListener
 * - Exception handling
 * - Clean user interface design
 */
public class LoginFrame extends JFrame {
    // GUI Components
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton clearButton;
    private JLabel statusLabel;
    private JLabel iconLabel;
    
    // Application components
    private SessionManager sessionManager;
    private Authentication authentication;
    
    /**
     * Constructor - Sets up the login frame
     */
    public LoginFrame() {
        // Initialize SessionManager and Authentication
        sessionManager = new SessionManager();
        authentication = new Authentication(sessionManager);
        
        // Add default admin to the system
        try {
            sessionManager.addUser("admin", "admin123", "ADMIN");
        } catch (IllegalArgumentException e) {
            // Admin already exists or other error - ignore
        }
        
        // Setup the frame
        setTitle("User Session Tracker - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
        
        // Initialize components
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        // Set visible
        setVisible(true);
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Panels
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        loginPanel.setBackground(new Color(248, 248, 248));
        
        // Title
        titleLabel = new JLabel("User Session Tracker", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        // Icon label
        iconLabel = new JLabel("🔐", JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        
        // Username components
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Password components
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setEchoChar('•'); // Hidden password input
        
        // Buttons
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(100, 35));
        
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clearButton.setBackground(new Color(231, 76, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setPreferredSize(new Dimension(100, 35));
        
        // Status label
        statusLabel = new JLabel("Enter credentials to login", JLabel.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(100, 100, 100));
    }
    
    /**
     * Set up the layout using GridBagLayout for the login panel
     */
    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Login Panel Layout
        // Row 0: Icon
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(iconLabel, gbc);
        
        // Row 1: Username Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.2;
        loginPanel.add(usernameLabel, gbc);
        
        // Row 1: Username Field
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.8;
        loginPanel.add(usernameField, gbc);
        
        // Row 2: Password Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        loginPanel.add(passwordLabel, gbc);
        
        // Row 2: Password Field
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.8;
        loginPanel.add(passwordField, gbc);
        
        // Row 3: Buttons
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);
        loginPanel.add(buttonPanel, gbc);
        
        // Row 4: Status
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(statusLabel, gbc);
        
        // Main Panel Layout
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    /**
     * Set up event handlers
     */
    private void setupEventHandlers() {
        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        // Clear button action
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        // Enter key handler on password field
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
        
        // Enter key handler on username field
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordField.requestFocus();
                }
            }
        });
    }
    
    /**
     * Handle login process
     */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("⚠️ Please enter both username and password");
            statusLabel.setForeground(new Color(231, 76, 60));
            return;
        }
        
        // Try to authenticate
        User authenticatedUser = authentication.authenticateUser(username, password);
        
        if (authenticatedUser != null) {
            // Login successful
            statusLabel.setText("✅ Login successful! Welcome, " + username);
            statusLabel.setForeground(new Color(46, 204, 113));
            
            // Create session for the user
            Session session = sessionManager.loginUser(username, password);
            
            // Close login frame and open dashboard
            this.dispose();
            new DashboardFrame(sessionManager, authenticatedUser);
        } else {
            // Login failed
            statusLabel.setText("❌ Invalid username or password");
            statusLabel.setForeground(new Color(231, 76, 60));
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }
    
    /**
     * Clear all input fields
     */
    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        statusLabel.setText("Enter credentials to login");
        statusLabel.setForeground(new Color(100, 100, 100));
        usernameField.requestFocus();
    }
}
