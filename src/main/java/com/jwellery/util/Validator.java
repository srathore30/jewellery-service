package com.jwellery.util;

public class Validator {
    public static boolean isValidMobileNo(Long mobileNo){
        return mobileNo != null && mobileNo.toString().length() == 10;
    }
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }
}
