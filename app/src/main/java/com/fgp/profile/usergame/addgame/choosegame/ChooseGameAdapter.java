package com.fgp.profile.usergame.addgame.choosegame;

import android.support.annotation.NonNull;
import android.support.transition.Slide;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fgp.R;
import com.fgp.model.Game;
import com.fgp.profile.usergame.addgame.inputduration.InputDurationFragment;

import java.util.List;

public class ChooseGameAdapter extends RecyclerView.Adapter<ChooseGameAdapter.ChooseGameViewHolder> {

    private List<Game> mGames;

    private Fragment mFragment;

    public ChooseGameAdapter(Fragment fragment, List<Game> games) {
        mGames = games;
        mFragment = fragment;
    }

    @NonNull
    @Override
    public ChooseGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_game,
                parent, false);
        return new ChooseGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseGameViewHolder holder, int position) {
        Game game = mGames.get(position);
        Glide.with(holder.itemView).load(game.getIcon()).into(holder.mLogo);
        holder.mName.setText(game.getName());

        holder.itemView.setOnClickListener(v -> {
            Slide exitTransition = new Slide();
            exitTransition.setMode(Slide.MODE_OUT);
            exitTransition.setSlideEdge(Gravity.START);
            mFragment.setExitTransition(exitTransition);

            Fragment fragment = InputDurationFragment.newInstance(game.getId());
            Slide enterTransition = new Slide();
            enterTransition.setMode(Slide.MODE_IN);
            enterTransition.setSlideEdge(Gravity.END);
            fragment.setEnterTransition(enterTransition);

            mFragment.getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.activity_add_user_game_container, fragment)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    class ChooseGameViewHolder extends RecyclerView.ViewHolder {

        private ImageView mLogo;

        private TextView mName;

        public ChooseGameViewHolder(View itemView) {
            super(itemView);
            mLogo = itemView.findViewById(R.id.item_choose_game_img_logo);
            mName = itemView.findViewById(R.id.item_choose_game_text_view_name);
        }
    }
}
