package com.mobile.harsoft.mymoviecatalogues.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    private SharedPreferences sharedPreferences;
    private Context context;

    private String KEY_DAILY = "daily";
    private String KEY_RELEASE = "release";

    public AppPreference(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public Boolean getDailyReminderMovie() {
        return sharedPreferences.getBoolean(KEY_DAILY, true);
    }

    public void setDailyReminderMovie(Boolean bool) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_DAILY, bool);
        editor.apply();
    }

    public Boolean getReleaseReminderMovie() {
        return sharedPreferences.getBoolean(KEY_RELEASE, true);
    }

    public void setReleaseReminderMovie(Boolean bool) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_RELEASE, bool);
        editor.apply();
    }
}
