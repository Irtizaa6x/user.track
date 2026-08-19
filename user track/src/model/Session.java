package model;

public class Session {
    private String username;
    private String ip;
    private String loginTime;
    private String status; // "Online" or "Offline"

    public Session(String username, String ip, String loginTime) {
        this.username = username;
        this.ip = ip;
        this.loginTime = loginTime;
        this.status = "Online"; // default
    }

    // Getters and setters
    public String getUsername() { return username; }
    public String getIp() { return ip; }
    public String getLoginTime() { return loginTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%-12s | IP: %-15s | Login: %s | Status: %s",
                username, ip, loginTime, status);
    }
}