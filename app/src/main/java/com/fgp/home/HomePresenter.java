package com.fgp.home;

import android.support.annotation.NonNull;

import com.fgp.R;
import com.fgp.util.RetrofitUtil;
import com.fgp.api.GameApi;
import com.fgp.model.Game;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;

    private List<Integer> mImageUrls;

    private GameApi mGameApi;

    public HomePresenter(HomeContract.View homeView) {
        mHomeView = homeView;
        mGameApi = RetrofitUtil.getInstance().create(GameApi.class);
    }

    @Override
    public void getHomeBannerImages() {
        mImageUrls = new LinkedList<>();

        mImageUrls.add(R.drawable.banner_world_of_warcraft);
        mImageUrls.add(R.drawable.banner_overwatch);
        mImageUrls.add(R.drawable.banner_league_of_legends);

        mHomeView.setHomeBannerImages(mImageUrls);
    }

    @Override
    public void getHomeGames() {
        Call<List<Game>> gameCall = mGameApi.getGameList();
        gameCall.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(@NonNull Call<List<Game>> call, @NonNull Response<List<Game>> response) {
                if (response.isSuccessful()){
                    mHomeView.setHomeGames(response.body());
                } else {
                    mHomeView.showGetGamesFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Game>> call, @NonNull Throwable t) {
                mHomeView.showNetworkError();
            }
        });
    }

}
