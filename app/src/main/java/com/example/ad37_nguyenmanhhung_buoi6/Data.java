package com.example.ad37_nguyenmanhhung_buoi6;

import java.util.regex.Pattern;

public class Data {

    //Password condition
    public static final Pattern PASSWORD_PATTERN
            = Pattern.compile("^" + //Begin
            "(?=.*[0-9])" +  //0-9
            "(?=.*[a-z])" + //a-z
            "(?=.*[A-Z])" + //A-Z
            "(?=.*[^\\t\\n\\x0B\\f\\r])" + //Have no white space
            "(?=.*[!@#$%^&*])" + //Have a special character
            ".{6,14}" + //Min length:6, Max length: 14
            "$"); //Finish

    //Username and Password
    public static final String username = "admin";
    public static final String password = "Admin123*";

    //Tag List
    public static final String[] tagList = {"Family", "Game", "Android", "VTC", "Friend"};

    //Week List
    public static final String[] weekList = {"Mon", "Tue", "Wed", "Thu",
            "Fri", "Sat", "Sun"};

    //Tune List
    public static final String[] tunes = {"Nexus Tune", "WindowPhone Tune",
            "Pep Tune", "Nokia Tune", "Other"};

    public static final String POSITIVE_BUTTON =  "OK";
    public static final String NEGATIVE_BUTTON =  "Cancel";
}
