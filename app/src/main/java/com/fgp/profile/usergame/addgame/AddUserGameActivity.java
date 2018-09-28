package com.fgp.profile.usergame.addgame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.fgp.R;
import com.fgp.profile.usergame.addgame.choosegame.ChooseGameFragment;

public class AddUserGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_game);
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment chooseGameFragment = ChooseGameFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_add_user_game_container, chooseGameFragment)
                .commit();
    }
}
