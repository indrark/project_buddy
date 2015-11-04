package com.njit.buddy.app.util;

/**
 * @author toyknight 11/2/2015.
 */
public class PasswordValidator {

    public boolean validate(String password) {
        return password.length() >= 8;
    }

}
