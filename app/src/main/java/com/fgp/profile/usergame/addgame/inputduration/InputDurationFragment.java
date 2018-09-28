package com.fgp.profile.usergame.addgame.inputduration;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.fgp.R;
import com.fgp.api.GameApi;
import com.fgp.api.UserApi;
import com.fgp.model.UserGame;
import com.fgp.util.RetrofitUtil;
import com.fgp.util.StorageUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputDurationFragment extends Fragment {

    private UserApi mUserApi;

    public InputDurationFragment() {

    }

    private static final String KEY_GAME_ID = "gameId";

    private int mGameId;

    private Dialog mLoadingDialog;

    public static InputDurationFragment newInstance(int gameId) {
        InputDurationFragment fragment = new InputDurationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_GAME_ID, gameId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserApi = RetrofitUtil.getInstance().create(UserApi.class);
        if (getArguments() != null) {
            mGameId = getArguments().getInt(KEY_GAME_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user_game_input_duration, container,
                false);
        mLoadingDialog = new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .title("Add Game...")
                .content(R.string.dialog_please_wait)
                .build();

        BootstrapEditText durationText = view.findViewById(R.id
                .fragment_add_user_game_input_duration_edit_text_duration);
        BootstrapButton addBtn = view.findViewById(R.id
                .fragment_add_user_game_input_duration_btn_add);
        addBtn.setOnClickListener(v -> {
            mLoadingDialog.show();
            UserGame userGame = new UserGame(
                    StorageUtil.getUsername(getActivity()),
                    mGameId,
                    Integer.parseInt(durationText.getText().toString()),
                    null,
                    null
            );
            Call<ResponseBody> addGameCall = mUserApi.addNewGame(userGame);
            addGameCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (mLoadingDialog.isShowing()) {
                        mLoadingDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        Activity activity = getActivity();
                        activity.setResult(Activity.RESULT_OK);
                        activity.finish();
                    } else {
                        Toast.makeText(getContext(), "failed to add game, please try again", Toast.LENGTH_SHORT)
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
        });
        return view;
    }
}
