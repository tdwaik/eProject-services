package com.thaer.jj.core.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public class Validator {

    public static boolean checkEmail(String email) {
        if(email == null || email.length() > 255) {
            return false;
        }

        Pattern pattern = Pattern.compile(RegexPatterns.EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(RegexPatterns.PHONE_NUMBER);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }
}
