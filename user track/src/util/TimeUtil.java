package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TimeUtil {
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Random random = new Random();

    public static String getCurrentTimestamp() {
        return DATE_FORMAT.format(new Date());
    }

    public static String getRandomPastTimestamp() {
        Calendar cal = Calendar.getInstance();
        int secondsAgo = random.nextInt(86400); // up to 24 hours
        cal.add(Calendar.SECOND, -secondsAgo);
        return DATE_FORMAT.format(cal.getTime());
    }
}