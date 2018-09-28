package com.fgp.profile.modify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.fgp.R;
import com.fgp.api.UserApi;
import com.fgp.model.User;
import com.fgp.util.RetrofitUtil;
import com.fgp.util.StorageUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyInfoActivity extends AppCompatActivity {

    private final static int REQUEST_CODE = 1;
    private RoundedImageView mIconRIV;
    private int mIcon;

    public static void startActivity(Context ctx) {
        ctx.startActivity(new Intent(ctx, ModifyInfoActivity.class));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        findViewById(R.id.activity_modify_info_btn_return).setOnClickListener(it -> onBackPressed());
        mIconRIV = findViewById(R.id.activity_modify_info_icon_riv);
        EditText label = findViewById(R.id.activity_modify_info_label_et);
        label.setText(StorageUtil.getIntroduction(this));
        mIcon = StorageUtil.getUserPortraitIndex(this);
        mIconRIV.setImageResource(StorageUtil.getPortraitByIndex(mIcon));

        mIconRIV.setOnClickListener(it -> startActivityForResult(new Intent(this, IconChooseActivity.class), REQUEST_CODE));

        findViewById(R.id.activity_modify_info_save).setOnClickListener(
                it -> {
                    User user = new User();
                    user.setId(StorageUtil.getUsername(ModifyInfoActivity.this));
                    user.setIcon(mIcon);
                    user.setLabel(label.getText().toString());

                    RetrofitUtil.getInstance().create(UserApi.class).modifyInfo(user).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful() && response.body()) {
                                Toast.makeText(ModifyInfoActivity.this, "Modify successful", Toast.LENGTH_SHORT);
                                StorageUtil.setUser(ModifyInfoActivity.this, user.getId(), user.getIcon(), user.getLabel());
                                onBackPressed();
                            } else {
                                Toast.makeText(ModifyInfoActivity.this, "Modify failed", Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(ModifyInfoActivity.this, "Network Error. ", Toast.LENGTH_SHORT);
                        }
                    });
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            mIcon = data.getIntExtra("icon", 0);
            mIconRIV.setImageResource(StorageUtil.getPortraitByIndex(mIcon));
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
