package com.fgp.user;

import android.app.Application;
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
import com.fgp.model.RegisterInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private SignApi mSignApi;

    private Dialog mLoadingDialog;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile_register, container, false);

        mLoadingDialog = new MaterialDialog.Builder(getContext())
                .progress(true, 0)
                .content(R.string.dialog_please_wait)
                .build();

        ImageView returnBtn = view.findViewById(R.id.fragment_profile_register_btn_return);
        returnBtn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        BootstrapEditText usernameEditText = view.findViewById(R.id
                .fragment_profile_register_edit_text_username);
        BootstrapEditText passwordEditText = view.findViewById(R.id
                .fragment_profile_register_edit_text_password);
        BootstrapButton registerBtn = view.findViewById(R.id
                .fragment_profile_register_btn_register);

        registerBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(usernameEditText.getText())) {
                Toast.makeText(getContext(), R.string.warn_empty_username, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(passwordEditText.getText())) {
                Toast.makeText(getContext(), R.string.warn_empty_password, Toast.LENGTH_SHORT).show();
            } else {
                mLoadingDialog.show();
                RegisterInfo registerInfo = new RegisterInfo(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        0,
                        "A happy player :)"
                );
                Call<ResponseBody> registerCall = mSignApi.register(registerInfo);
                registerCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (mLoadingDialog.isShowing()) {
                            mLoadingDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            //if register succeed
                            //store user's username & password which is used for future login
                            StorageUtil.setUser(getContext(),
                                    registerInfo.getUsername(),
                                    registerInfo.getIcon(),
                                    registerInfo.getLabel());
                            MyApplication.setLogin(true);
                            //back to profile
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        } else {
                            Toast.makeText(getContext(), "Register failed", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        if (mLoadingDialog.isShowing()) {
                            mLoadingDialog.dismiss();
                        }
                        Toast.makeText(getContext(), R.string.error_network, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
