package com.fgp.profile.usergame;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fgp.R;
import com.fgp.model.UserGame;
import com.fgp.util.CommonUtil;

import java.util.List;

public class UserGameAdapter extends RecyclerView.Adapter<UserGameAdapter.UserGameViewHolder> {

    private List<UserGame> mGames;

    public UserGameAdapter(List<UserGame> games) {
        mGames = games;
    }

    @NonNull
    @Override
    public UserGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_game,
                parent, false);
        return new UserGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserGameViewHolder holder, int position) {
        UserGame game = mGames.get(position);
        Glide.with(holder.itemView)
                .load(game.getGame().getIcon())
                .into(holder.mLogoImg);
        holder.mTimeText.setText(CommonUtil.getPlayTimeStr(game.getDuration()));
        holder.mNameText.setText(game.getGame().getName());
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    class UserGameViewHolder extends RecyclerView.ViewHolder{

        private ImageView mLogoImg;

        private TextView mTimeText;

        private TextView mNameText;

        UserGameViewHolder(View itemView) {
            super(itemView);
            mLogoImg = itemView.findViewById(R.id.item_user_game_img_logo);
            mTimeText = itemView.findViewById(R.id.item_user_game_text_view_play_time);
            mNameText = itemView.findViewById(R.id.item_user_game_text_view_name);
        }

    }
}
