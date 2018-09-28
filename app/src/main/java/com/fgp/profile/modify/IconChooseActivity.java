package com.fgp.profile.modify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.fgp.R;

public class IconChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_icon);
        findViewById(R.id.activity_choose_icon_btn_return).setOnClickListener(it -> onBackPressed());
        ViewGroup icons = findViewById(R.id.activity_choose_icon_icons_gl);
        for (int i = 0; i < icons.getChildCount(); i++) {
            final int idx = i;
            icons.getChildAt(i).setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra("icon", idx);
                setResult(RESULT_OK, intent);
                onBackPressed();
            });
        }
    }
}
