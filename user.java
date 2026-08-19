package main.java;

/**
 * User class represents a basic user in the system.
 * This is the base class for all users, demonstrating encapsulation.
 */
public class User {
    // Private fields - Encapsulation
    private String userId;
    private String username;
    private String password;
    private String role;
    
    /**
     * Default constructor
     */
    public User() {
        this.role = "USER";
    }
    
    /**
     * Parameterized constructor
     * @param userId Unique identifier for the user
     * @param username Username for login
     * @param password Password for login
     */
    public User(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = "USER";
    }
    
    /**
     * Parameterized constructor with role
     */
    public User(String userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    // Getters and Setters - Encapsulation
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * Displays user information - Can be overridden by subclasses
     * Demonstrates method that can be overridden (polymorphism)
     */
    public String displayInfo() {
        return "User ID: " + userId + ", Username: " + username + ", Role: " + role;
    }
    
    /**
     * Validates if the password matches
     * @param inputPassword The password to validate against
     * @return true if password matches, false otherwise
     */
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}
