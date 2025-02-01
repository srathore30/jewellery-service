package com.jewellery.util;

import java.security.SecureRandom;

public class CodeGenerator {
    public static String generateItemCode() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            code.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
        }
        return code.toString();
    }
}
