package com.fgp.profile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.fgp.MainActivity;
import com.fgp.MyApplication;
import com.fgp.R;
import com.fgp.model.UserGame;
import com.fgp.players.GamePlayersActivity;
import com.fgp.profile.modify.ModifyInfoActivity;
import com.fgp.profile.usergame.UserGameActivity;
import com.fgp.user.LoginFragment;
import com.fgp.user.RegisterFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private static final long TRANSITION_DURATION = 600;
    private ProfileContract.Presenter mPresenter;
    private RoundedImageView mPortraitImg;

    private TextView mNameText;

    private TextView mIntroductionText;

    private LinearLayout mGoodAtGamesLayout;

    private RelativeLayout mUserGamesEntry;

    private BootstrapButton mLogoutButton;

    private Dialog mLoadingDialog;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (MyApplication.isLogin()) {// if user exists, show profile page
            view = inflater.inflate(R.layout.fragment_profile, container, false);

            mLoadingDialog = new MaterialDialog.Builder(getContext())
                    .progress(true, 0)
                    .title("Loading Your Games...")
                    .content(R.string.dialog_please_wait)
                    .build();

            mNameText = view.findViewById(R.id.fragment_profile_text_view_name);

            mIntroductionText = view.findViewById(R.id.fragment_profile_text_view_introduction);

            mPortraitImg = view.findViewById(R.id.fragment_profile_img_portrait);

            mGoodAtGamesLayout = view.findViewById(R.id.fragment_profile_layout_good_at);

            mUserGamesEntry = view.findViewById(R.id.fragment_profile_layout_games);
            mUserGamesEntry.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), UserGameActivity.class);
                startActivity(intent);
            });

            view.findViewById(R.id.fragment_profile_btn_edit_info).setOnClickListener(it -> ModifyInfoActivity.startActivity(getContext()));

            mLogoutButton = view.findViewById(R.id.fragment_profile_btn_logout);
            mLogoutButton.setOnClickListener(v ->
                    ((MainActivity) Objects.requireNonNull(getActivity())).logout());

        } else { // if user doesn't exists, show not_login page
            view = inflater.inflate(R.layout.fragment_profile_not_login, container, false);

            TextView welcomeText = view.findViewById(R.id.fragment_profile_not_login_text_view_welcome);

            BootstrapButton loginBtn = view.findViewById(R.id.fragment_profile_not_login_btn_login);
            loginBtn.setOnClickListener(v -> replaceFragmentWithTransition(LoginFragment.newInstance(), welcomeText, loginBtn));

            BootstrapButton registerBtn = view.findViewById(R.id.fragment_profile_not_login_btn_register);
            registerBtn.setOnClickListener(v -> replaceFragmentWithTransition(RegisterFragment.newInstance(), welcomeText, registerBtn));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.isLogin()) {
            mPresenter.getUserInfo();
            mPresenter.getUserGames();
        }
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setPortrait(int drawableRes) {
        Glide.with(this).load(drawableRes).into(mPortraitImg);
    }

    @Override
    public void setName(String username) {
        mNameText.setText(username);
    }

    @Override
    public void setIntroduction(String introduction) {
        mIntroductionText.setText(introduction);
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void setGoodAtGames(List<UserGame> goodAtGames) {
        for (int i = 0; i < Math.min(3, goodAtGames.size()); i++) {
            ImageView logo = (ImageView) mGoodAtGamesLayout.getChildAt(i);
            UserGame userGame = goodAtGames.get(i);
            Glide.with(getContext())
                    .load(userGame.getGame().getIcon())
                    .into(logo);
            logo.setOnClickListener(v -> {
                GamePlayersActivity.startActivity(userGame.getGame().getName(), userGame.getGameId(), getActivity());
            });
        }
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getContext(), R.string.error_network, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadGoodAtGamesFailed() {
        Toast.makeText(getContext(), "Load Good At Games Failed", Toast.LENGTH_SHORT).show();
    }

    private void replaceFragmentWithTransition(Fragment nextFragment, View... sharedElements) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        Fade exitFade = new Fade();
        exitFade.setDuration(TRANSITION_DURATION);
        this.setExitTransition(exitFade);

        Fade enterFade = new Fade();
        enterFade.setDuration(TRANSITION_DURATION);
        nextFragment.setEnterTransition(enterFade);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//after LOLLIPOP supports shared element transition

            TransitionSet enterTransitionSet = new TransitionSet();
            enterTransitionSet.addTransition(TransitionInflater.from(getContext())
                    .inflateTransition(android.R.transition.move));
            enterTransitionSet.setDuration(TRANSITION_DURATION);

            nextFragment.setSharedElementEnterTransition(enterTransitionSet);

            for (View sharedElement : sharedElements) {
                transaction.addSharedElement(sharedElement, sharedElement.getTransitionName());
            }

        }

        transaction.addToBackStack(null);
        transaction.replace(R.id.activity_main_container, nextFragment);
        transaction.commitAllowingStateLoss();
    }
}
