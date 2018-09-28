package com.fgp.user_comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.fgp.R;
import com.fgp.api.UserApi;
import com.fgp.model.Comment;
import com.fgp.util.RetrofitUtil;
import com.fgp.util.StorageUtil;
import com.fgp.visit.VisitCommentsAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCommentActivity extends AppCompatActivity {

    public static void startActivity(Context ctx) {
        ctx.startActivity(new Intent(ctx, UserCommentActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comment);
        findViewById(R.id.activity_user_comment_btn_return).setOnClickListener(it -> {
            onBackPressed();
        });
        RecyclerView commentsRV = findViewById(R.id.activity_user_comment_comments_rv);
        VisitCommentsAdapter adapter = new VisitCommentsAdapter();
        commentsRV.setLayoutManager(new LinearLayoutManager(this));
        commentsRV.setAdapter(adapter);

        View emptyView = findViewById(R.id.activity_user_comment_empty_tv);

        UserApi api = RetrofitUtil.getInstance().create(UserApi.class);
        api.getComments(StorageUtil.getUsername(this)).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    adapter.setDataList(response.body());
                    if (response.body().size() > 0) {
                        commentsRV.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.INVISIBLE);
                    } else {
                        emptyView.setVisibility(View.VISIBLE);
                        commentsRV.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(UserCommentActivity.this, "Error load user's comments", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(UserCommentActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
