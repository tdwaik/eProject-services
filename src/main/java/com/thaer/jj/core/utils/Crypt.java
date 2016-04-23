package com.thaer.jj.core.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since April 23, 2016.
 */
public class Crypt {

    public static String hashPasword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(15));
    }

    public static boolean checkPassword(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
    }

}
