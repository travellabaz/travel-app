package az.travellab.ms_travel_application.util;

import lombok.experimental.UtilityClass;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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

    public static String getRandomNumbers() {
        return String.valueOf(random.nextInt(900000000) + 100000000);
    }
}