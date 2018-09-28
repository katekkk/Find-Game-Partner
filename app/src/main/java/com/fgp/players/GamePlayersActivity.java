package com.fgp.players;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.fgp.R;
import com.fgp.api.GameApi;
import com.fgp.model.UserGame;
import com.fgp.search.SearchResultAdapter;
import com.fgp.util.CommonUtil;
import com.fgp.util.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamePlayersActivity extends AppCompatActivity {

    private final static String KEY_KEYWORD = "keyword";
    private final static String KEY_ID = "game_id";
    private SearchResultAdapter mSearchResultAdapter;
    private GameApi mGameApi;
    private RecyclerView mSearchResultRV;
    private View mEmptyView;

    public static void startActivity(String keyword, int gameId, Context ctx) {
        Intent intent = new Intent(ctx, GamePlayersActivity.class);
        intent.putExtra(KEY_KEYWORD, keyword);
        intent.putExtra(KEY_ID, gameId);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameApi = RetrofitUtil.getInstance().create(GameApi.class);
        setContentView(R.layout.activity_search_result);
        // search in result
        SearchView searchView = findViewById(R.id.activity_search_result_search_view);
        ImageView closeBtn = searchView.findViewById(R.id.search_close_btn);
        closeBtn.setImageDrawable(null);
        closeBtn.setEnabled(false);
        searchView.onActionViewExpanded();
        String keyword = getIntent().getStringExtra(KEY_KEYWORD);
        searchView.setQuery(keyword, false);
        CommonUtil.disableView(searchView);
        //
        mSearchResultRV = findViewById(R.id.activity_search_result_result_list_rv);
        mSearchResultRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultAdapter = new SearchResultAdapter();
        mSearchResultRV.setAdapter(mSearchResultAdapter);

        mEmptyView = findViewById(R.id.activity_search_result_empty_tv);
        int id = getIntent().getIntExtra(KEY_ID, 0);
        query(id);
        // clear current focus to avoid keyboard
        CommonUtil.clearFocus(searchView);
    }

    public void query(int id) {
        Call<List<UserGame>> searchCall = mGameApi.getGamePlayers(id);
        searchCall.enqueue(new Callback<List<UserGame>>() {
            @Override
            public void onResponse(Call<List<UserGame>> call, Response<List<UserGame>> response) {
                if (response.isSuccessful()) {
                    mSearchResultAdapter.setDataList(response.body());
                    if (response.body().size() > 0) {
                        mSearchResultRV.setVisibility(View.VISIBLE);
                        mEmptyView.setVisibility(View.INVISIBLE);
                    } else {
                        mEmptyView.setVisibility(View.VISIBLE);
                        mSearchResultRV.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(GamePlayersActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserGame>> call, Throwable t) {
                Toast.makeText(GamePlayersActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
