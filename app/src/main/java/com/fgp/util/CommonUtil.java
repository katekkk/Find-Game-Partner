package com.fgp.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class CommonUtil {

    public static String getPlayTimeStr(int duration) {
        if (duration == 0) {
            return "0 day";
        }
        int day = duration % 30;
        int month = duration / 30;
        int year = month / 12;
        month %= 12;
        StringBuilder builder = new StringBuilder();
        builder.append("Play: ");
        if (year > 0) {
            builder.append(year).append("year ");
        }
        if (month > 0) {
            builder.append(month).append("mon ");
        }
        if (day > 0) {
            builder.append(day).append("day");
        }
        return builder.toString();
    }

    public static void disableView(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i ++) {
                disableView(((ViewGroup) view).getChildAt(i));
            }
        } else {
            view.setEnabled(false);
        }
    }

    public static void clearFocus(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i ++) {
                clearFocus(((ViewGroup) view).getChildAt(i));
            }
        } else {
            view.clearFocus();
        }
    }

    public static void closeKeyboard(Activity act) {
        View view = act.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
