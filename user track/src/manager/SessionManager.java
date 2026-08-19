package manager;

import model.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SessionManager {
    private List<Session> sessions;
    private Random random = new Random();

    public SessionManager() {
        sessions = new ArrayList<>();
    }

    public void addSession(String username, String ip, String loginTime) {
        sessions.add(new Session(username, ip, loginTime));
    }

    public boolean removeSession(String username) {
        return sessions.removeIf(s -> s.getUsername().equalsIgnoreCase(username));
    }

    public List<Session> getSessions() {
        return new ArrayList<>(sessions);
    }

    public int getSessionCount() {
        return sessions.size();
    }

    public int getOnlineCount() {
        return (int) sessions.stream().filter(s -> "Online".equals(s.getStatus())).count();
    }

    public int getOfflineCount() {
        return sessions.size() - getOnlineCount();
    }

    public void clear() {
        sessions.clear();
    }

    /** Toggles a random session's status between Online and Offline. */
    public void toggleRandomStatus() {
        if (sessions.isEmpty()) return;
        Session s = sessions.get(random.nextInt(sessions.size()));
        if ("Online".equals(s.getStatus())) {
            s.setStatus("Offline");
        } else {
            s.setStatus("Online");
        }
    }

    /** Returns a formatted string of all sessions (for status bar or debug). */
    public String getFormattedSessionList() {
        if (sessions.isEmpty()) return "No sessions.";
        StringBuilder sb = new StringBuilder();
        sb.append("Online User Sessions:\n");
        sb.append("------------------------------------------------------------\n");
        int serial = 1;
        for (Session s : sessions) {
            sb.append(String.format("%02d. %s\n", serial++, s.toString()));
        }
        sb.append("------------------------------------------------------------\n");
        sb.append("Total: " + sessions.size() + " (Online: " + getOnlineCount() + ", Offline: " + getOfflineCount() + ")");
        return sb.toString();
    }
}