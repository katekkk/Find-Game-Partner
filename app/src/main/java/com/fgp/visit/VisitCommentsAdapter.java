package com.fgp.visit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fgp.R;
import com.fgp.model.Comment;
import com.fgp.util.StorageUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class VisitCommentsAdapter extends RecyclerView.Adapter<VisitCommentsAdapter.VisitCommentsHolder> {

    private List<Comment> mDataList;

    public VisitCommentsAdapter() {
        mDataList = new ArrayList<>();
    }

    public void setDataList(List<Comment> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VisitCommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visit_comment, parent, false);
        return new VisitCommentsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitCommentsHolder holder, int position) {
        holder.bindData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class VisitCommentsHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mUserIconRIV;
        private TextView mUsernameTV;
        private TextView mContentTV;

        VisitCommentsHolder(View itemView) {
            super(itemView);
            mUserIconRIV = itemView.findViewById(R.id.item_comment_user_icon_riv);
            mUsernameTV = itemView.findViewById(R.id.item_comment_username_tv);
            mContentTV = itemView.findViewById(R.id.item_comment_content);
        }

        void bindData(Comment data) {
            mUserIconRIV.setImageResource(StorageUtil.getPortraitByIndex(data.getFromUser().getIcon()));
            mUsernameTV.setText(data.getFromUser().getId());
            mContentTV.setText(data.getContent());
        }

    }

}
