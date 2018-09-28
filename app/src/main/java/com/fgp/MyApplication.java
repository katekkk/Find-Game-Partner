package com.fgp;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context sContext;

    private static boolean isLogin;

    public static void setLogin(boolean isLogin) {
        MyApplication.isLogin = isLogin;
    }

    public static boolean isLogin() {
        return isLogin;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
