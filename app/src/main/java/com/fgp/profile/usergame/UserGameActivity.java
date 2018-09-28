package com.fgp.profile.usergame;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fgp.R;
import com.fgp.api.UserApi;
import com.fgp.model.UserGame;
import com.fgp.profile.usergame.addgame.AddUserGameActivity;
import com.fgp.util.RetrofitUtil;
import com.fgp.util.StorageUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserGameActivity extends AppCompatActivity {

    private RecyclerView mContentRecyclerView;

    private UserApi mUserApi;

    private ImageView mAddGameBtn;

    private Dialog mLoadingDialog;

    private static final int REQUEST_CODE_ADD_GAME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_game);

        mContentRecyclerView = findViewById(R.id.activity_user_game_recycler_view_content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContentRecyclerView.setLayoutManager(layoutManager);

        mUserApi = RetrofitUtil.getInstance().create(UserApi.class);

        mLoadingDialog = new MaterialDialog.Builder(this)
                .title("Loading Play Time...")
                .content(R.string.dialog_please_wait)
                .build();

        mAddGameBtn = findViewById(R.id.activity_user_game_btn_add_game);
        mAddGameBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUserGameActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_GAME);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoadingDialog.show();
        Call<List<UserGame>> getUserGameCall = mUserApi.getUserGames(StorageUtil.getUsername
                (this));
        getUserGameCall.enqueue(new Callback<List<UserGame>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserGame>> call, @NonNull Response<List<UserGame>> response) {
                mLoadingDialog.dismiss();
                if (response.isSuccessful()) {
                    UserGameAdapter adapter = new UserGameAdapter(response.body());
                    mContentRecyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "load failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserGame>> call, @NonNull Throwable t) {
                mLoadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_GAME) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "add game successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
