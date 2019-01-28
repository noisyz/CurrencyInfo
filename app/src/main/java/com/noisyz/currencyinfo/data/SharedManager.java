package com.noisyz.currencyinfo.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedManager {

    private static final String PREFERENCES = "CurrencyInfoPreferences", KEY_INFO_ENABLED = "info_enabled_";
    private static SharedPreferences instance;

    public static void init(Context context) {
        instance = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void setInfoEnabled(String info, boolean enabled) {
        instance.edit().putBoolean(KEY_INFO_ENABLED + info, enabled).apply();
    }

    public static boolean isInfoEnabled(String info) {
        return instance.getBoolean(KEY_INFO_ENABLED + info, true);
    }

}
