package com.medeasy.util;

import java.util.prefs.Preferences;

public class LoginInfoSave {
    private static final Preferences prefs = Preferences.userRoot().node("Medeasy/config");
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String ROLE = "role";
    private static final String USER_ID = "userID";

    public static void saveLoginInfo(String email, String password,String role,String userID) {
        prefs.put(EMAIL_KEY, email);
        prefs.put(PASSWORD_KEY, password);
        prefs.put(ROLE,role);
        prefs.put(USER_ID,userID);
    }

    public static String[] getLoginInfo() {
        String[] loginInfo = new String[4];
        loginInfo[0] = prefs.get(EMAIL_KEY, null);
        loginInfo[1] = prefs.get(PASSWORD_KEY, null);
        loginInfo[2] = prefs.get(ROLE, null);
        loginInfo[3] = prefs.get(USER_ID, null);
        return loginInfo;
    }


    public static void clearLoginInfo() {
        prefs.remove(EMAIL_KEY);
        prefs.remove(PASSWORD_KEY);
        prefs.remove(ROLE);
        prefs.remove(USER_ID);
    }
}

