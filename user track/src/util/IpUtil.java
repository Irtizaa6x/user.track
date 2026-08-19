package util;

import java.util.Random;

/**
 * Utility class for generating random IP addresses.
 * This class provides static methods to generate IP addresses
 * in the format "192.168.xxx.xxx" (similar to the original C project).
 */
public class IpUtil {
    private static final Random random = new Random();

    /**
     * Generates a random IP address in the 192.168.x.x range.
     *
     * @return a random IP address as a string (e.g., "192.168.42.137")
     */
    public static String generateRandomIp() {
        int a = random.nextInt(256);   // 0-255
        int b = random.nextInt(256);   // 0-255
        return String.format("192.168.%d.%d", a, b);
    }
}