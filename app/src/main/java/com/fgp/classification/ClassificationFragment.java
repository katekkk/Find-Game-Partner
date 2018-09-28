package com.fgp.classification;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fgp.R;
import com.fgp.api.GameApi;
import com.fgp.model.Game;
import com.fgp.util.RetrofitUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassificationFragment extends Fragment {

    public ClassificationFragment() {
    }

    public static ClassificationFragment newInstance() {
        return new ClassificationFragment();
    }

    private RecyclerView mContentRecyclerView;

    private GameApi mGameApi;

    private Dialog mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameApi = RetrofitUtil.getInstance().create(GameApi.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classification, container, false);
        // show loading
        mLoadingDialog = new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .title("Loading Classifications...")
                .content(R.string.dialog_please_wait)
                .build();

        mLoadingDialog.show();

        mContentRecyclerView = view.findViewById(R.id
                .fragment_classification_recycler_view_content);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContentRecyclerView.setLayoutManager(layoutManager);
        // load classification
        Call<Map<String, List<Game>>> getGameClassificationCall = mGameApi.getGameClassification();
        getGameClassificationCall.enqueue(new Callback<Map<String, List<Game>>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, List<Game>>> call, @NonNull Response<Map<String, List<Game>>> response) {
                if (mLoadingDialog.isShowing()){
                    mLoadingDialog.dismiss();
                }
                if (response.isSuccessful()){
                    List<String> names = new LinkedList<>();
                    List<List<Game>> games = new LinkedList<>();
                    for (String name: response.body().keySet()){
                        names.add(name);
                        games.add(response.body().get(name));
                    }
                    ClassificationAdapter adapter = new ClassificationAdapter(names, games);
                    mContentRecyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(), "failed to load classifications", Toast
                            .LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, List<Game>>> call, @NonNull Throwable t) {
                if (mLoadingDialog.isShowing()){
                    mLoadingDialog.dismiss();
                }
                Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
