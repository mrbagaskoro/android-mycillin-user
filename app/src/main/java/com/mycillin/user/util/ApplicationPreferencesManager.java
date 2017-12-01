package com.mycillin.user.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPreferencesManager {

    public static final String KEY_USER_PREFERED_LANGUAGE = "USER_PREFERED_LANGUAGE";
    private static final String KEY_IS_SELECT_LANGUAGE = "IS_SELECT_LANGUAGE";
    private static final String KEY_IS_INTRO_DONE = "IS_INTRO_DONE";

    public static final String DEFAULT_VALUE = "-1";
    private static final String PREF_NAME = "ApplicationPreferencesManager";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public ApplicationPreferencesManager(Context con) {
        this.context = con;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void selectLanguage() {
        editor.putBoolean(KEY_IS_SELECT_LANGUAGE, true);

        editor.commit();
    }

    public boolean isSelectLanguage() {
        return sharedPreferences.getBoolean(KEY_IS_SELECT_LANGUAGE, false);
    }

    public void introDone() {
        editor.putBoolean(KEY_IS_INTRO_DONE, true);

        editor.commit();
    }

    public boolean isIntroDone() {
        return sharedPreferences.getBoolean(KEY_IS_INTRO_DONE, false);
    }
}
