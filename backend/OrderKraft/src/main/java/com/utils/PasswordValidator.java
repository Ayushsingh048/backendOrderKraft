package com.utils;

public class PasswordValidator {

    public static boolean isValidPassword(String password) {
        if (password == null) return false;

        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*");
    }
}
