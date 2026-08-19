package model;

/**
 * Represents a pending request from a user trying to connect.
 * Contains username, IP address, and request time.
 */
public class Request {
    private String username;
    private String ip;
    private String requestTime;

    public Request(String username, String ip, String requestTime) {
        this.username = username;
        this.ip = ip;
        this.requestTime = requestTime;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public String getRequestTime() {
        return requestTime;
    }

    @Override
    public String toString() {
        return String.format("User: %-10s | IP: %-15s | Requested: %s",
                username, ip, requestTime);
    }
}