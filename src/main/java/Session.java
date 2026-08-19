package main.java;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Session class represents a user session with login time and IP address
 * Demonstrates encapsulation and business logic for session management
 */
public class Session {
    // Private fields - Encapsulation
    private User user;
    private String ipAddress;
    private String loginTime;
    private boolean isActive;
    private String sessionId;
    
    /**
     * Constructor creates a new session for a user
     * Automatically generates IP address and timestamp
     * @param user The user for whom the session is created
     */
    public Session(User user) {
        this.user = user;
        this.ipAddress = generateSimulatedIP();
        this.loginTime = getCurrentTimestamp();
        this.isActive = true;
        this.sessionId = generateSessionId();
    }
    
    /**
     * Generates a simulated IP address
     * Format: xxx.xxx.xxx.xxx where xxx is between 0-255
     * @return Simulated IP address string
     */
    private String generateSimulatedIP() {
        Random random = new Random();
        int part1 = random.nextInt(256);
        int part2 = random.nextInt(256);
        int part3 = random.nextInt(256);
        int part4 = random.nextInt(256);
        
        // Avoid localhost (127.0.0.1) and broadcast addresses for realism
        if (part1 == 127 && part2 == 0 && part3 == 0 && part4 == 1) {
            part1 = 192;
            part2 = 168;
            part3 = 1;
            part4 = random.nextInt(255) + 1;
        }
        
        return part1 + "." + part2 + "." + part3 + "." + part4;
    }
    
    /**
     * Gets current timestamp in formatted string
     * @return Formatted date-time string
     */
    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    
    /**
     * Generates a unique session ID
     * @return Session ID string
     */
    private String generateSessionId() {
        Random random = new Random();
        int id = random.nextInt(1000000);
        return "SES-" + String.format("%06d", id);
    }
    
    // Getters - Encapsulation
    public User getUser() {
        return user;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public String getLoginTime() {
        return loginTime;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    /**
     * Ends the session (marks as inactive)
     */
    public void endSession() {
        this.isActive = false;
    }
    
    /**
     * Gets session duration (simplified)
     * @return Formatted duration string
     */
    public String getSessionDuration() {
        // In a real implementation, this would calculate duration
        // For simulation, we return a placeholder
        return "Active";
    }
    
    /**
     * Gets session information as a formatted string
     * @return Session details
     */
    public String getSessionInfo() {
        return "Session ID: " + sessionId + 
               "\nUser: " + user.getUsername() + 
               "\nIP: " + ipAddress + 
               "\nLogin Time: " + loginTime + 
               "\nStatus: " + (isActive ? "Active" : "Ended");
    }
    
    @Override
    public String toString() {
        return user.getUsername() + " | " + ipAddress + " | " + loginTime + " | " + (isActive ? "Active" : "Ended");
    }
}
