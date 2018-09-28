package com.fgp.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static Retrofit mInstance;

    public static Retrofit getInstance() {
        if (mInstance == null) {
            mInstance = new Retrofit.Builder().baseUrl("http://47.94.14.51/").addConverterFactory
                    (GsonConverterFactory.create()).build();
        }
        return mInstance;
    }
}
