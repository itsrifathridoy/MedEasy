package com.medeasy.util;

import java.util.prefs.Preferences;

public class LoginInfoSave {
    private static final Preferences prefs = Preferences.userRoot().node("Medeasy/config");
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    public static void saveLoginInfo(String username, String password) {
        prefs.put(EMAIL_KEY, username);
        prefs.put(PASSWORD_KEY, password);
    }

    public static String[] getLoginInfo() {
        String[] loginInfo = new String[2];
        loginInfo[0] = prefs.get(EMAIL_KEY, null);
        loginInfo[1] = prefs.get(PASSWORD_KEY, null);
        return loginInfo;
    }

    public static void clearLoginInfo() {
        prefs.remove(EMAIL_KEY);
        prefs.remove(PASSWORD_KEY);
    }
}

