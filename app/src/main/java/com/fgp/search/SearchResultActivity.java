package com.fgp.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fgp.R;
import com.fgp.api.GameApi;
import com.fgp.model.UserGame;
import com.fgp.util.CommonUtil;
import com.fgp.util.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    private final static String KEY_KEYWORD = "keyword";

    public static void startActivity(String keyword, Context ctx) {
        Intent intent = new Intent(ctx, SearchResultActivity.class);
        intent.putExtra(KEY_KEYWORD, keyword);
        ctx.startActivity(intent);
    }

    private SearchResultAdapter mSearchResultAdapter;
    private RecyclerView mSearchResultRV;
    private GameApi mGameApi;
    private View mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameApi = RetrofitUtil.getInstance().create(GameApi.class);
        setContentView(R.layout.activity_search_result);
        // search in result
        SearchView searchView = findViewById(R.id.activity_search_result_search_view);
        searchView.onActionViewExpanded();
        String keyword = getIntent().getStringExtra(KEY_KEYWORD);
        CommonUtil.clearFocus(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { return true; }
        });
        mSearchResultRV = findViewById(R.id.activity_search_result_result_list_rv);
        mSearchResultRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultAdapter = new SearchResultAdapter();
        mSearchResultRV.setAdapter(mSearchResultAdapter);
        mEmptyView = findViewById(R.id.activity_search_result_empty_tv);
        // set with query
        searchView.setQuery(keyword, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtil.closeKeyboard(this);
    }

    public void query(String keyword) {
        Call<List<UserGame>> searchCall = mGameApi.search(keyword);
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
                    Toast.makeText(SearchResultActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserGame>> call, Throwable t) {
                Toast.makeText(SearchResultActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
