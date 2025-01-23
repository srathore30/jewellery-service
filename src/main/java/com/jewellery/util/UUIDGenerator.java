package com.jewellery.util;
import java.security.SecureRandom;

public class UUIDGenerator {
    private static final String CHARACTERS = "0123456789";
    private static final SecureRandom random = new SecureRandom();
    private static final int length = 6;

    public static Long generateRandomString() {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 1; i <= length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return Long.parseLong(sb.toString());
    }
}
