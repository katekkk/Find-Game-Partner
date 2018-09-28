package com.fgp.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fgp.R;
import com.fgp.model.Game;
import com.fgp.model.UserGame;
import com.fgp.players.GamePlayersActivity;
import com.fgp.search.SearchResultActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {

    private Banner mBanner;

    private LinearLayout mHotGameLayout;

    private SearchView mSearchView;

    private static final int BANNER_DELAY_TIME = 3000;

    private static final int MAX_HOT_GAMES = 6;

    private HomeContract.Presenter mPresenter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSearchView = view.findViewById(R.id.fragment_home_search_view);
        mSearchView.onActionViewExpanded();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // query logic is handled in SearchResultActivity
                SearchResultActivity.startActivity(query, HomeFragment.this.getContext());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        mHotGameLayout = view.findViewById(R.id.fragment_home_layout_hot_games);

        mBanner = view.findViewById(R.id.fragment_home_banner);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setDelayTime(BANNER_DELAY_TIME);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
        mBanner.requestFocus();
        mPresenter.getHomeBannerImages();
        mPresenter.getHomeGames();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBanner.releaseBanner();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setHomeBannerImages(List<Integer> bannerImages) {
        mBanner.setImageLoader(new HomeBannerImageLoader());
        mBanner.setImages(bannerImages);
        mBanner.start();
    }

    @Override
    public void setHomeGames(List<Game> games) {
        int count = 0;
        for (int i = 0; i < Math.min(mHotGameLayout.getChildCount(), MAX_HOT_GAMES); i++) {
            LinearLayout hotGameLine = (LinearLayout) mHotGameLayout.getChildAt(i);
            for (int j = 0; j < hotGameLine.getChildCount(); j++) {
                ImageView hotGameImage = (ImageView) hotGameLine.getChildAt(j);
                Game each = games.get(count);
                count ++;
                Glide.with(hotGameImage)
                        .load(each.getIcon())
                        .into(hotGameImage);
                hotGameImage.setOnClickListener(it -> {
                    GamePlayersActivity.startActivity(each.getName(), each.getId(), getContext());
                });
            }
        }
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getContext(), R.string.error_network, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGetGamesFailed() {
        Toast.makeText(getContext(), "Failed to get games", Toast.LENGTH_SHORT).show();
    }

}
