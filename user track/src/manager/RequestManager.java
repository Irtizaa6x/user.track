package manager;

import model.Request;
import util.RandomDataGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RequestManager {
    private List<Request> pendingRequests;
    private Random random = new Random();

    public RequestManager() {
        pendingRequests = new ArrayList<>();
    }

    public void addRequest(String username, String ip, String requestTime) {
        pendingRequests.add(new Request(username, ip, requestTime));
    }

    public boolean removeRequest(String username) {
        return pendingRequests.removeIf(request ->
                request.getUsername().equalsIgnoreCase(username)
        );
    }

    public List<Request> getRequests() {
        return new ArrayList<>(pendingRequests);
    }

    public boolean hasRequest(String username) {
        return pendingRequests.stream()
                .anyMatch(r -> r.getUsername().equalsIgnoreCase(username));
    }

    public int getRequestCount() {
        return pendingRequests.size();
    }

    public void clear() {
        pendingRequests.clear();
    }

    /** Adds a single random request (uses RandomDataGenerator). */
    public void addRandomRequest() {
        String username = RandomDataGenerator.generateRandomUsername();
        String ip = RandomDataGenerator.generateRandomIp();
        String time = RandomDataGenerator.generateRandomTimestamp();
        addRequest(username, ip, time);
    }

    /** Removes a random request (if any exist). */
    public void removeRandomRequest() {
        if (!pendingRequests.isEmpty()) {
            int index = random.nextInt(pendingRequests.size());
            pendingRequests.remove(index);
        }
    }

    /** Returns a random request (for internal use). */
    public Request getRandomRequest() {
        if (pendingRequests.isEmpty()) return null;
        return pendingRequests.get(random.nextInt(pendingRequests.size()));
    }
}