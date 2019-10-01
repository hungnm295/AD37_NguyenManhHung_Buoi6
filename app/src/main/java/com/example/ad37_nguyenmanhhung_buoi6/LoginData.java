package com.example.ad37_nguyenmanhhung_buoi6;

import java.util.regex.Pattern;

public class LoginData {
    public static final Pattern PASSWORD_PATTERN
            = Pattern.compile("^" +
            "(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[^\\t\\n\\x0B\\f\\r])" +
            "(?=.*[!@#$%^&*])" +
            ".{6,14}" +
            "$");
    public static final String username = "admin";
    public static final String password = "Admin123*";
}
