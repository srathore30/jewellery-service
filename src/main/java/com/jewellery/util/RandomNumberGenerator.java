package com.jewellery.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomNumberGenerator {

    // Method to generate an 8-digit random number
    public static int generateEightDigitNumber() {
        Random random = new Random();
        int number = 10000000 + random.nextInt(90000000); // Ensures the number is 8 digits
        return number;
    }
}
