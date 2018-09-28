package com.fgp.home;

import com.fgp.BaseView;
import com.fgp.model.Game;
import com.fgp.model.UserGame;

import java.util.List;

public interface HomeContract {

    interface Presenter {

        void getHomeBannerImages();

        void getHomeGames();

    }

    interface View extends BaseView<Presenter> {
        void setHomeBannerImages(List<Integer> bannerImages);

        void setHomeGames(List<Game> games);

        void showNetworkError();

        void showGetGamesFailed();

    }

}
