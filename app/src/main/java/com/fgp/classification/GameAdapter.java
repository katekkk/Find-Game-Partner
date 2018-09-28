package com.fgp.classification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fgp.R;
import com.fgp.model.Game;
import com.fgp.players.GamePlayersActivity;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> mGames;

    public GameAdapter(List<Game> games) {
        mGames = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_classification_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = mGames.get(position);
        Glide.with(holder.itemView).load(game.getIcon()).into(holder.mLogo);
        holder.itemView.setOnClickListener(v -> {
            GamePlayersActivity.startActivity(game.getName(), game.getId(), holder.itemView.getContext());
        });
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        private ImageView mLogo;

        public GameViewHolder(View itemView) {
            super(itemView);
            mLogo = itemView.findViewById(R.id.item_classification_game_img_logo);
        }
    }
}
