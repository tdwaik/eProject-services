package com.thaer.jj.core.lib;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class Validator {

    public static boolean checkEmail(String email) {
        if(email == null || email.length() > 255) {
            return false;
        }

        return email.matches(RegexPatterns.EMAIL);
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches(RegexPatterns.PHONE_NUMBER);
    }

    public static boolean checkPhoneNumber(Long phoneNumber) {
        if(phoneNumber == null) {
            return false;
        }
        return Long.toString(phoneNumber).matches(RegexPatterns.PHONE_NUMBER);
    }
}
