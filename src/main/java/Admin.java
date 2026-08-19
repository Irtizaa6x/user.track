package main.java;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Admin class extends User class - Demonstrating Inheritance
 * Admin is a specialized type of User with additional attributes and behaviors
 */
public class Admin extends User {
    // Additional attributes specific to Admin
    private String adminLevel;
    private String lastLogin;
    
    /**
     * Constructor for creating an Admin
     * Uses super() to call parent constructor
     */
    public Admin(String userId, String username, String password) {
        // Calling parent constructor
        super(userId, username, password, "ADMIN");
        this.adminLevel = "SUPER";
        this.lastLogin = "Never";
    }
    
    /**
     * Overloaded constructor with admin level
     */
    public Admin(String userId, String username, String password, String adminLevel) {
        super(userId, username, password, "ADMIN");
        this.adminLevel = adminLevel;
        this.lastLogin = "Never";
    }
    
    // Getters and Setters
    public String getAdminLevel() {
        return adminLevel;
    }
    
    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
    
    public String getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    /**
     * Updates the last login timestamp to current time
     */
    public void updateLastLogin() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.lastLogin = now.format(formatter);
    }
    
    /**
     * Overriding the displayInfo() method - Demonstrating Method Overriding (Polymorphism)
     * This method provides specialized information for Admin objects
     */
    @Override
    public String displayInfo() {
        return "Admin ID: " + getUserId() + 
               ", Username: " + getUsername() + 
               ", Admin Level: " + adminLevel + 
               ", Last Login: " + lastLogin;
    }
    
    /**
     * Special method only for Admin - Demonstrates inheritance extension
     */
    public String getAdminDashboardInfo() {
        return "=== Admin Dashboard ===\n" +
               "Username: " + getUsername() + "\n" +
               "Admin Level: " + adminLevel + "\n" +
               "Last Login: " + lastLogin;
    }
    
    @Override
    public String toString() {
        return "ADMIN: " + getUsername() + " (Level: " + adminLevel + ")";
    }
}
