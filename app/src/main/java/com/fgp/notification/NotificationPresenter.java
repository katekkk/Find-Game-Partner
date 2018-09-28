package com.fgp.notification;

import com.fgp.R;

public class NotificationPresenter implements NotificationContract.Presenter {

    private NotificationContract.View mNotificationView;

    public NotificationPresenter(NotificationContract.View notificationView) {
        mNotificationView = notificationView;
    }

}
