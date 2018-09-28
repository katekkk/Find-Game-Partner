package com.fgp.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fgp.MyApplication;
import com.fgp.R;
import com.fgp.user_comment.UserCommentActivity;
import com.fgp.visit.VisitActivity;

public class NotificationFragment extends Fragment implements NotificationContract.View {

    private NotificationContract.Presenter mPresenter;

    public NotificationFragment() {

    }

    public static NotificationFragment newInstance(){
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        View commentsEntry = view.findViewById(R.id.fragment_notification_entry_comments);
        commentsEntry.setOnClickListener(v -> {
            // must login
            if (MyApplication.isLogin()) {
                UserCommentActivity.startActivity(getContext());
            } else {
                Toast.makeText(NotificationFragment.this.getContext(), "Please login first", Toast.LENGTH_SHORT).show();
            }
        });
        View noticeEntry = view.findViewById(R.id.fragment_notification_entry_notice);
        noticeEntry.setOnClickListener(v -> {
            Toast.makeText(NotificationFragment.this.getContext(), "Unsupported yet.", Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    @Override
    public void setPresenter(NotificationContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
