package com.fgp.api;

import com.fgp.model.Game;
import com.fgp.model.UserGame;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GameApi {

    @GET("game/getGameClassification")
    Call<Map<String, List<Game>>> getGameClassification();

    @GET("game/getGameList")
    Call<List<Game>> getGameList();

    @GET("game/search")
    Call<List<UserGame>> search(@Query("keyword") String keyword);


    @GET("game/getGamePlayers")
    Call<List<UserGame>> getGamePlayers(@Query("gameId") int gameId);

}
