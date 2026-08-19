package main.java;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * SessionManager class manages users and sessions
 * This is the core business logic class demonstrating:
 * - Collections (ArrayList)
 * - Exception handling
 * - Encapsulation
 * - Business logic organization
 */
public class SessionManager {
    // Private collections - Encapsulation
    private ArrayList<User> users;
    private ArrayList<Session> activeSessions;
    private ArrayList<Session> sessionHistory;
    
    // User ID counter for generating unique IDs
    private int userIdCounter;
    
    /**
     * Constructor initializes collections
     */
    public SessionManager() {
        this.users = new ArrayList<>();
        this.activeSessions = new ArrayList<>();
        this.sessionHistory = new ArrayList<>();
        this.userIdCounter = 1001;
    }
    
    /**
     * Adds a new user to the system
     * @param username The username
     * @param password The password
     * @param role The user role ("USER" or "ADMIN")
     * @return true if user added successfully, false otherwise
     * @throws IllegalArgumentException if username already exists or invalid
     */
    public boolean addUser(String username, String password, String role) throws IllegalArgumentException {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }
        
        // Check if username already exists
        if (findUser(username) != null) {
            throw new IllegalArgumentException("Username '" + username + "' already exists");
        }
        
        // Create new user
        String userId = "U" + userIdCounter++;
        User newUser;
        
        if ("ADMIN".equalsIgnoreCase(role)) {
            newUser = new Admin(userId, username, password);
        } else {
            newUser = new User(userId, username, password, "USER");
        }
        
        users.add(newUser);
        return true;
    }
    
    /**
     * Removes a user from the system
     * @param username The username to remove
     * @return true if user removed successfully, false otherwise
     * @throws IllegalArgumentException if user not found
     */
    public boolean removeUser(String username) throws IllegalArgumentException {
        // Use iterator for safe removal
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equals(username)) {
                // Remove all active sessions for this user
                removeUserSessions(username);
                iterator.remove();
                return true;
            }
        }
        throw new IllegalArgumentException("User '" + username + "' not found");
    }
    
    /**
     * Removes all sessions for a specific user
     * @param username The username
     */
    private void removeUserSessions(String username) {
        Iterator<Session> iterator = activeSessions.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getUser().getUsername().equals(username)) {
                session.endSession();
                sessionHistory.add(session);
                iterator.remove();
            }
        }
    }
    
    /**
     * Finds a user by username
     * @param username The username to search for
     * @return User object if found, null otherwise
     */
    public User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Logs in a user - creates a new session
     * @param username The username
     * @param password The password
     * @return Session object if login successful, null otherwise
     */
    public Session loginUser(String username, String password) {
        User user = findUser(username);
        
        if (user == null) {
            return null;
        }
        
        // Check if user is already logged in
        for (Session session : activeSessions) {
            if (session.getUser().getUsername().equals(username) && session.isActive()) {
                return null; // User already logged in
            }
        }
        
        // Create new session
        Session newSession = new Session(user);
        activeSessions.add(newSession);
        
        return newSession;
    }
    
    /**
     * Logs out a user - ends their session
     * @param username The username
     * @return true if logout successful, false otherwise
     */
    public boolean logoutUser(String username) {
        Iterator<Session> iterator = activeSessions.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getUser().getUsername().equals(username) && session.isActive()) {
                session.endSession();
                sessionHistory.add(session);
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets all active sessions
     * @return ArrayList of active sessions
     */
    public ArrayList<Session> getActiveSessions() {
        return new ArrayList<>(activeSessions);
    }
    
    /**
     * Gets all users in the system
     * @return ArrayList of all users
     */
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    /**
     * Gets all regular users (non-admin)
     * @return ArrayList of regular users
     */
    public ArrayList<User> getRegularUsers() {
        ArrayList<User> regularUsers = new ArrayList<>();
        for (User user : users) {
            if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
                regularUsers.add(user);
            }
        }
        return regularUsers;
    }
    
    /**
     * Gets session history (ended sessions)
     * @return ArrayList of ended sessions
     */
    public ArrayList<Session> getSessionHistory() {
        return new ArrayList<>(sessionHistory);
    }
    
    /**
     * Gets the total number of users
     * @return User count
     */
    public int getTotalUsers() {
        return users.size();
    }
    
    /**
     * Gets the number of active sessions
     * @return Active session count
     */
    public int getActiveSessionCount() {
        int count = 0;
        for (Session session : activeSessions) {
            if (session.isActive()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Gets the number of regular users
     * @return Regular user count
     */
    public int getRegularUserCount() {
        return getRegularUsers().size();
    }
    
    /**
     * Clears all inactive sessions from active sessions list
     */
    public void clearInactiveSessions() {
        Iterator<Session> iterator = activeSessions.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (!session.isActive()) {
                sessionHistory.add(session);
                iterator.remove();
            }
        }
    }
    
    /**
     * Generates a summary report of the system
     * @return Formatted summary string
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== SYSTEM REPORT ===\n");
        report.append("Total Users: ").append(getTotalUsers()).append("\n");
        report.append("Regular Users: ").append(getRegularUserCount()).append("\n");
        report.append("Active Sessions: ").append(getActiveSessionCount()).append("\n");
        report.append("\n--- Active Sessions ---\n");
        
        for (Session session : activeSessions) {
            if (session.isActive()) {
                report.append(session.toString()).append("\n");
            }
        }
        
        return report.toString();
    }
    
    /**
     * Checks if a user is currently logged in
     * @param username The username
     * @return true if user has an active session, false otherwise
     */
    public boolean isUserLoggedIn(String username) {
        for (Session session : activeSessions) {
            if (session.getUser().getUsername().equals(username) && session.isActive()) {
                return true;
            }
        }
        return false;
    }
}
