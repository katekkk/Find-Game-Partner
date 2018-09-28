package com.fgp.profile;

import android.support.annotation.DrawableRes;

import com.fgp.BaseView;
import com.fgp.model.UserGame;

import java.util.List;

public interface ProfileContract {

    interface Presenter {
        void getUserInfo();

        void getUserGames();
    }

    interface View extends BaseView<Presenter> {
        void setPortrait(@DrawableRes int drawableRes);

        void setName(String username);

        void setIntroduction(String introduction);

        void showLoading();

        void hideLoading();

        void setGoodAtGames(List<UserGame> goodAtGames);

        void showNetworkError();

        void showLoadGoodAtGamesFailed();
    }
}
