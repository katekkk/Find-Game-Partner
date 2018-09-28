package com.fgp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.fgp.classification.ClassificationFragment;
import com.fgp.home.HomeFragment;
import com.fgp.home.HomePresenter;
import com.fgp.notification.NotificationFragment;
import com.fgp.notification.NotificationPresenter;
import com.fgp.profile.ProfileFragment;
import com.fgp.profile.ProfilePresenter;
import com.fgp.util.StorageUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mNavigationView;

    private HomeFragment mHomeFragment;

    private ClassificationFragment mClassificationFragment;

    private NotificationFragment mNotificationFragment;

    private ProfileFragment mProfileFragment;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start login.
        //Here simply check if user ever logged in
        if (!MyApplication.isLogin() && StorageUtil.isUserExists(this)) {
            MyApplication.setLogin(true);
        }

        mFragmentManager = getSupportFragmentManager();

        mNavigationView = findViewById(R.id.activity_main_navigation);
        mNavigationView.setOnNavigationItemSelectedListener(this);

        mHomeFragment = HomeFragment.newInstance();
        HomePresenter homePresenter = new HomePresenter(mHomeFragment);
        mHomeFragment.setPresenter(homePresenter);
        mFragmentManager.beginTransaction().add(R.id.activity_main_container, mHomeFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (mNavigationView.getSelectedItemId() != item.getItemId()) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (mHomeFragment == null) {
                        mHomeFragment = HomeFragment.newInstance();
                        HomePresenter homePresenter = new HomePresenter(mHomeFragment);
                        mHomeFragment.setPresenter(homePresenter);
                    }
                    replaceFragment(mHomeFragment);
                    return true;
                case R.id.navigation_classification:
                    if (mClassificationFragment == null){
                        mClassificationFragment = ClassificationFragment.newInstance();
                    }
                    replaceFragment(mClassificationFragment);
                    return true;
                case R.id.navigation_notification:
                    if (mNotificationFragment == null) {
                        mNotificationFragment = NotificationFragment.newInstance();
                        NotificationPresenter notificationPresenter = new NotificationPresenter
                                (mNotificationFragment);
                        mNotificationFragment.setPresenter(notificationPresenter);
                    }
                    replaceFragment(mNotificationFragment);
                    return true;
                case R.id.navigation_profile:
                    if (mProfileFragment == null) {
                        mProfileFragment = ProfileFragment.newInstance();
                        ProfilePresenter profilePresenter = new ProfilePresenter(mProfileFragment);
                        mProfileFragment.setPresenter(profilePresenter);
                    }
                    replaceFragment(mProfileFragment);
                    return true;
            }
        }
        return false;
    }

    private void replaceFragment(Fragment next) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_container, next)
                .commit();
    }

    public void logout(){
        StorageUtil.clearUser(this);
        MyApplication.setLogin(false);
        mProfileFragment = ProfileFragment.newInstance();
        ProfilePresenter profilePresenter = new ProfilePresenter(mProfileFragment);
        mProfileFragment.setPresenter(profilePresenter);
        replaceFragment(mProfileFragment);
    }
}
