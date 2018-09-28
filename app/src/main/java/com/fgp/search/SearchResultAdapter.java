package com.fgp.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fgp.R;
import com.fgp.model.UserGame;
import com.fgp.util.CommonUtil;
import com.fgp.util.StorageUtil;
import com.fgp.visit.VisitActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {

    private List<UserGame> mDataList;

    public SearchResultAdapter() {
        mDataList = new ArrayList<>();
    }

    public void setDataList(List<UserGame> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {
        holder.bindData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class SearchResultHolder extends RecyclerView.ViewHolder {
        private UserGame mData;
        private RoundedImageView mUserIconRIV;
        private TextView mUserLabelTV;
        private TextView mUsernameTV;
        private TextView mDurationTV;

        SearchResultHolder(View itemView) {
            super(itemView);
            mUserIconRIV = itemView.findViewById(R.id.item_result_user_icon_riv);
            mUsernameTV = itemView.findViewById(R.id.item_result_username_tv);
            mUserLabelTV = itemView.findViewById(R.id.item_result_user_label_tv);
            mDurationTV = itemView.findViewById(R.id.item_result_play_time_tv);
            itemView.setOnClickListener(v -> {
                VisitActivity.startActivity(v.getContext(), mData.getUser());
            });
        }

        void bindData(UserGame data) {
            this.mData = data;
            mUserIconRIV.setImageResource(StorageUtil.getPortraitByIndex(data.getUser().getIcon()));
            mUsernameTV.setText(mData.getUserId());
            mUserLabelTV.setText(mData.getUser().getLabel());
            mDurationTV.setText(CommonUtil.getPlayTimeStr(data.getDuration()));
        }
    }

}
