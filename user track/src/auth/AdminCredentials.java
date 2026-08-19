package auth;

/**
 * Stores hardcoded admin credentials.
 * This class separates credential data from authentication logic.
 * Following OOP principles, credentials are kept in one place for easy maintenance.
 */
public class AdminCredentials {
    // Predefined admin usernames and corresponding passwords
    private static final String[] ADMIN_USERS = {
            "admin",
            "admin"
    };

    private static final String[] ADMIN_PASSWORDS = {
            "admin",
            "admin"
    };

    /**
     * Returns the array of valid admin usernames.
     *
     * @return array of admin usernames
     */
    public static String[] getAdminUsers() {
        return ADMIN_USERS.clone(); // defensive copy
    }

    /**
     * Returns the array of valid admin passwords.
     * The index matches the corresponding username in ADMIN_USERS.
     *
     * @return array of admin passwords
     */
    public static String[] getAdminPasswords() {
        return ADMIN_PASSWORDS.clone(); // defensive copy
    }

    /**
     * Returns the total number of admin accounts.
     *
     * @return admin count
     */
    public static int getAdminCount() {
        return ADMIN_USERS.length;
    }
}