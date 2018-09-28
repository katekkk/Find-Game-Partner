package com.fgp.profile;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fgp.MyApplication;
import com.fgp.api.UserApi;
import com.fgp.model.UserGame;
import com.fgp.util.RetrofitUtil;
import com.fgp.util.StorageUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mProfileView;

    private UserApi mUserApi;

    public ProfilePresenter(ProfileContract.View profileView) {
        mProfileView = profileView;
        mUserApi = RetrofitUtil.getInstance().create(UserApi.class);
    }

    @Override
    public void getUserInfo() {
        Context context = MyApplication.getContext();
        mProfileView.setName(StorageUtil.getUsername(context));
        mProfileView.setIntroduction(StorageUtil.getIntroduction(context));
        mProfileView.setPortrait(StorageUtil.getUserPortrait(context));
    }

    @Override
    public void getUserGames() {
        Context context = MyApplication.getContext();
        Call<List<UserGame>> getUserGamesCall = mUserApi.getUserGames(StorageUtil.getUsername
                (context));
        getUserGamesCall.enqueue(new Callback<List<UserGame>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserGame>> call, @NonNull Response<List<UserGame>> response) {
                if (response.isSuccessful()){
                    mProfileView.setGoodAtGames(response.body());
                }else {
                    mProfileView.showLoadGoodAtGamesFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserGame>> call, @NonNull Throwable t) {
                mProfileView.showNetworkError();
            }
        });
    }

}
