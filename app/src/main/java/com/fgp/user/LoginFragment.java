package com.fgp.user;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.fgp.MyApplication;
import com.fgp.R;
import com.fgp.util.RetrofitUtil;
import com.fgp.util.StorageUtil;
import com.fgp.api.SignApi;
import com.fgp.model.LoginInfo;
import com.fgp.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment{

    private SignApi mSignApi;

    private Dialog mLoadingDialog;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignApi = RetrofitUtil.getInstance().create(SignApi.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_login, container, false);

        mLoadingDialog = new MaterialDialog.Builder(getContext())
                .progress(true, 0)
                .content(R.string.dialog_please_wait)
                .build();

        BootstrapEditText username = view.findViewById(R.id
                .fragment_profile_login_edit_text_username);
        BootstrapEditText password = view.findViewById(R.id
                .fragment_profile_login_edit_text_password);
        BootstrapButton loginBtn = view.findViewById(R.id.fragment_profile_login_btn_login);
        loginBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(username.getText())){
                Toast.makeText(getContext(), R.string.warn_empty_username, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password.getText())){
                Toast.makeText(getContext(), R.string.warn_empty_password, Toast.LENGTH_SHORT).show();
            } else {
                mLoadingDialog.show();
                LoginInfo loginInfo = new LoginInfo(
                        username.getText().toString(),
                        password.getText().toString()
                );

                Call<User> loginCall = mSignApi.login(loginInfo);
                loginCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if (mLoadingDialog.isShowing()){
                            mLoadingDialog.dismiss();
                        }
                        if (response.isSuccessful()){
                            User user = response.body();
                            //if login succeed, store user info
                            StorageUtil.setUser(getContext(),
                                    loginInfo.getUsername(),
                                    user.getIcon(),
                                    user.getLabel());
                            MyApplication.setLogin(true);
                            //back to profile
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        } else {
                            Toast.makeText(getContext(), "Login failed. Please check your " +
                                    "username & password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        if (mLoadingDialog.isShowing()){
                            mLoadingDialog.dismiss();
                        }
                        Toast.makeText(getContext(), R.string.error_network, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        ImageView returnBtn = view.findViewById(R.id.fragment_profile_login_btn_return);
        returnBtn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
