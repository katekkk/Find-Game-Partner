package com.fgp.visit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fgp.MyApplication;
import com.fgp.R;
import com.fgp.api.UserApi;
import com.fgp.model.Comment;
import com.fgp.model.User;
import com.fgp.util.RetrofitUtil;
import com.fgp.util.StorageUtil;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitActivity extends AppCompatActivity {

    private final static String KEY_INFO = "info";


    public static void startActivity(Context ctx, User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        Intent intent = new Intent(ctx, VisitActivity.class);
        intent.putExtra(KEY_INFO, json);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        Gson gson = new Gson();
        User user = gson.fromJson(getIntent().getStringExtra(KEY_INFO), User.class);

        ((RoundedImageView)findViewById(R.id.activity_visit_img_portrait)).setImageResource(StorageUtil.getPortraitByIndex(user.getIcon()));
        ((TextView) findViewById(R.id.activity_visit_text_view_name)).setText(user.getId());
        ((TextView) findViewById(R.id.activity_visit_text_view_introduction)).setText(user.getLabel());
        RecyclerView lv = findViewById(R.id.activity_visit_comments_rv);
        VisitCommentsAdapter adapter = new VisitCommentsAdapter();
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.setAdapter(adapter);

        UserApi userApi = RetrofitUtil.getInstance().create(UserApi.class);
        findViewById(R.id.activity_visit_add_comment).setOnClickListener(it -> {
            if (MyApplication.isLogin()) {
                final EditText editText = new EditText(VisitActivity.this);
                new AlertDialog.Builder(VisitActivity.this).
                        setTitle("Input Comment").
                        setView(editText).
                        setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String comment = editText.getText().toString().trim();
                                if (comment.length() > 200) {
                                    Toast.makeText(VisitActivity.this, "Comment's length should be less than 200 words", Toast.LENGTH_SHORT).show();
                                } else {
                                    Comment body = new Comment();
                                    body.setFromId(StorageUtil.getUsername(VisitActivity.this));
                                    body.setContent(comment);
                                    body.setTargetId(user.getId());
                                    userApi.sendComment(body).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(VisitActivity.this, "Send Successful", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(VisitActivity.this, "Error send comments", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(VisitActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }).show();
            } else {
                Toast.makeText(VisitActivity.this, "Please login first", Toast.LENGTH_SHORT).show();
            }
        });

        userApi.getComments(user.getId()).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    Log.d(VisitActivity.class.getName(), "onResponse: " + response.body());
                     adapter.setDataList(response.body());
                } else {
                    Toast.makeText(VisitActivity.this, "Error load user's comments", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(VisitActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
