package com.fgp.api;

import com.fgp.model.LoginInfo;
import com.fgp.model.RegisterInfo;
import com.fgp.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignApi {

    @POST("verify/login")
    Call<User> login(@Body LoginInfo loginInfo);

    @POST("verify/register")
    Call<ResponseBody> register(@Body RegisterInfo registerInfo);
}
