package auth;

/**
 * Handles authentication of admin users.
 * This class separates the authentication logic from credential storage.
 * It checks the provided username and password against the stored admin credentials.
 */
public class AdminAuthenticator {

    /**
     * Authenticates an admin user by checking the provided username and password.
     *
     * @param username the admin username entered
     * @param password the admin password entered
     * @return true if the username exists and the password matches, false otherwise
     */
    public static boolean authenticate(String username, String password) {
        String[] users = AdminCredentials.getAdminUsers();
        String[] passwords = AdminCredentials.getAdminPasswords();

        for (int i = 0; i < users.length; i++) {
            if (users[i].equals(username) && passwords[i].equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given username is a valid admin username.
     * Useful for validation before checking the password.
     *
     * @param username the username to check
     * @return true if the username exists in the admin list
     */
    public static boolean isValidAdminUser(String username) {
        String[] users = AdminCredentials.getAdminUsers();
        for (String user : users) {
            if (user.equals(username)) {
                return true;
            }
        }
        return false;
    }
}