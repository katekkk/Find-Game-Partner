package com.fgp.classification;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fgp.R;
import com.fgp.model.Game;
import com.fgp.players.GamePlayersActivity;

import java.util.List;
import java.util.Map;

public class ClassificationAdapter extends RecyclerView.Adapter<ClassificationAdapter.ClassificationViewHolder> {

    private List<String> mClassificationNames;
    private List<List<Game>> mClassificationGames;

    public ClassificationAdapter(List<String> classificationNames, List<List<Game>> classificationGames) {
        mClassificationNames = classificationNames;
        mClassificationGames = classificationGames;
    }

    @NonNull
    @Override
    public ClassificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_classification, parent, false);
        return new ClassificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassificationViewHolder holder, int position) {
        String name = mClassificationNames.get(position);
        holder.mName.setText(name);
        List<Game> games = mClassificationGames.get(position);
        GridLayoutManager layoutManager = new GridLayoutManager(holder.itemView.getContext(), 3);
        holder.mRecyclerView.setLayoutManager(layoutManager);

        GameAdapter adapter = new GameAdapter(games);
        holder.mRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mClassificationGames.size();
    }

    class ClassificationViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;

        private RecyclerView mRecyclerView;

        public ClassificationViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.item_classification_name);

            mRecyclerView = itemView.findViewById(R.id.item_classification_recycler_view_game);
        }
    }
}
