package main.java;

import java.util.ArrayList;

/**
 * Authentication class handles login and validation logic
 * Demonstrates encapsulation and business logic separation
 */
public class Authentication {
    // Private fields
    private Admin admin;
    private SessionManager sessionManager;
    
    // Default admin credentials
    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";
    
    /**
     * Constructor
     * @param sessionManager Reference to SessionManager for user validation
     */
    public Authentication(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        initializeAdmin();
    }
    
    /**
     * Initializes the default admin account
     */
    private void initializeAdmin() {
        // Create default admin if not already exists
        if (admin == null) {
            admin = new Admin("ADMIN001", DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD);
            admin.updateLastLogin();
        }
    }
    
    /**
     * Authenticates admin login
     * @param username Entered username
     * @param password Entered password
     * @return true if authentication successful, false otherwise
     */
    public boolean authenticateAdmin(String username, String password) {
        // First check default admin
        if (admin != null && admin.getUsername().equals(username) && 
            admin.validatePassword(password)) {
            admin.updateLastLogin();
            return true;
        }
        
        // Check if there's a registered admin in the system
        User user = sessionManager.findUser(username);
        if (user != null && user.getRole().equals("ADMIN")) {
            if (user.validatePassword(password)) {
                // Update admin object reference if needed
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Authenticates any user (admin or regular)
     * @param username Entered username
     * @param password Entered password
     * @return Authenticated User object or null if authentication fails
     */
    public User authenticateUser(String username, String password) {
        // Check admin first
        if (admin != null && admin.getUsername().equals(username) && 
            admin.validatePassword(password)) {
            admin.updateLastLogin();
            return admin;
        }
        
        // Check regular users through SessionManager
        User user = sessionManager.findUser(username);
        if (user != null && user.validatePassword(password)) {
            return user;
        }
        
        return null; // Authentication failed
    }
    
    /**
     * Validates if the username meets criteria
     * @param username The username to validate
     * @return true if valid, false otherwise
     */
    public boolean validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        // Username must be at least 3 characters
        if (username.length() < 3) {
            return false;
        }
        // Username should only contain alphanumeric characters and underscores
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            return false;
        }
        return true;
    }
    
    /**
     * Validates if the password meets criteria
     * @param password The password to validate
     * @return true if valid, false otherwise
     */
    public boolean validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        // Password must be at least 4 characters
        if (password.length() < 4) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if a username already exists in the system
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    public boolean isUsernameExists(String username) {
        if (admin != null && admin.getUsername().equals(username)) {
            return true;
        }
        return sessionManager.findUser(username) != null;
    }
    
    /**
     * Gets the current admin
     * @return Admin object
     */
    public Admin getAdmin() {
        return admin;
    }
    
    /**
     * Resets the default admin password (for demonstration purposes)
     * @param newPassword New password for default admin
     */
    public void resetAdminPassword(String newPassword) {
        if (admin != null) {
            admin.setPassword(newPassword);
        }
    }
}
