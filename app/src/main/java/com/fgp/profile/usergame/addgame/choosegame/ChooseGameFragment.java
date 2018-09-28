package com.fgp.profile.usergame.addgame.choosegame;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Slide;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.fgp.R;
import com.fgp.api.GameApi;
import com.fgp.model.Game;
import com.fgp.profile.usergame.addgame.inputduration.InputDurationFragment;
import com.fgp.util.RetrofitUtil;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseGameFragment extends Fragment {

    public ChooseGameFragment() {
    }

    public static ChooseGameFragment newInstance() {
        ChooseGameFragment fragment = new ChooseGameFragment();
        return fragment;
    }

    private static final int SPAN_COUNT = 3;

    private RecyclerView mGamesRecyclerView;

    private GameApi mGameApi;

    private Dialog mLoadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameApi = RetrofitUtil.getInstance().create(GameApi.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user_game_choose_game, container, false);
        mLoadingDialog = new MaterialDialog.Builder(getContext())
                .progress(true, 0)
                .title("Loading Games...")
                .content(R.string.dialog_please_wait)
                .build();
        mLoadingDialog.show();

        mGamesRecyclerView = view.findViewById(R.id
                .fragment_add_user_game_choose_game_recycler_view_games);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mGamesRecyclerView.setLayoutManager(layoutManager);

        Call<List<Game>> getGamesCall = mGameApi.getGameList();
        getGamesCall.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(@NonNull Call<List<Game>> call, @NonNull Response<List<Game>> response) {
                if (mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    ChooseGameAdapter chooseGameAdapter = new ChooseGameAdapter(ChooseGameFragment.this,
                            response.body());
                    mGamesRecyclerView.setAdapter(chooseGameAdapter);
                } else {
                    Toast.makeText(getActivity(), "Failed to get games", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Game>> call, @NonNull Throwable t) {
                if (mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
