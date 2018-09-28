package com.fgp.util;

import android.content.Context;
import android.support.annotation.DrawableRes;

import com.fgp.R;

public class StorageUtil {

    private static final String FILE_NAME = "com.fgp";

    private static final String KEY_IS_USER_EXISTS = "isUserExists";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PORTRAIT = "portrait";
    private static final String KEY_INTRODUCTION = "introduction";

    private static final int[] USER_PORTRAIT_RESOURCES = {
            R.drawable.portrait_1,
            R.drawable.portrait_2,
            R.drawable.portrait_3,
            R.drawable.portrait_4
    };

    public static @DrawableRes
    int getPortraitByIndex(int index) {
        if (index < 0 || index >= USER_PORTRAIT_RESOURCES.length) {
            return USER_PORTRAIT_RESOURCES[0];
        } else {
            return USER_PORTRAIT_RESOURCES[index];
        }
    }

    public static boolean isUserExists(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean
                (KEY_IS_USER_EXISTS, false);
    }

    public static void clearUser(Context context) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }

    public static void setUser(Context context, String username, int portraitIndex, String introduction) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_IS_USER_EXISTS, true)
                .putString(KEY_USERNAME, username)
                .putInt(KEY_PORTRAIT, portraitIndex)
                .putString(KEY_INTRODUCTION, introduction)
                .apply();
    }


    public static String getUsername(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getString(KEY_USERNAME, "");
    }

    public static String getIntroduction(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getString(KEY_INTRODUCTION, "");
    }

    public static int getUserPortraitIndex(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getInt(KEY_PORTRAIT, 0);
    }


    public static int getUserPortrait(Context context) {
        return USER_PORTRAIT_RESOURCES[context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getInt
                (KEY_PORTRAIT, 0)];
    }

}
