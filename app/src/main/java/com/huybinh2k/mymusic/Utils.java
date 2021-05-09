package com.huybinh2k.mymusic;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    private static final String MUSIC_SHARED_PREFERENCES ="music_shared_preferences";
    private static SharedPreferences mPreferences;

    public static void saveInt(Context context ,int i, String pref){
        mPreferences = context.getSharedPreferences(MUSIC_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(pref, i);
        editor.apply();
    }

    public static int getInt(Context context,String pref){
        mPreferences = context.getSharedPreferences(MUSIC_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return mPreferences.getInt(pref, 2);
    }



}
