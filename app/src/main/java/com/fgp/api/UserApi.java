package com.fgp.api;

import com.fgp.model.Comment;
import com.fgp.model.User;
import com.fgp.model.UserGame;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("user/addNewGame")
    Call<ResponseBody> addNewGame(@Body UserGame info);

    @GET("user/getComments")
    Call<List<Comment>> getComments(@Query("username") String username);

    @GET("user/getUserGames")
    Call<List<UserGame>> getUserGames(@Query("username") String username);

    @GET("user/getUserInfo")
    Call<User> getUserInfo(@Query("username") String username);

    @POST("user/sendComment")
    Call<ResponseBody> sendComment(@Body Comment info);

    @POST("user/modifyInfo")
    Call<Boolean> modifyInfo(@Body User info);

}
