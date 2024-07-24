package az.travellab.ms_travel_application.util;

import lombok.experimental.UtilityClass;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;

@UtilityClass
public class SecureRandomUtil {
    private static final SecureRandom random;

    static {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            secureRandom = new SecureRandom();
        }
        random = secureRandom;
    }

    public static int getRandomDigit() {
        return random.nextInt(9) + 1;
    }

    public static String generateUniqueNumber(String format) {
        long currentTimeNs = Instant.now().getNano();

        long restOfNumber = currentTimeNs % 10000000;
        long uniqueNumber = getRandomDigit() * 10000000L + restOfNumber;

        int randomDigit = getRandomDigit();

        return String.format(format, uniqueNumber, randomDigit);
    }
}