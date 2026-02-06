package com.chocolatada.notime.notification_detail.domain

import com.chocolatada.notime.common.data.model.Notification
import com.chocolatada.notime.notification_detail.data.model.NotificationDetail

class NotificationDetailMapper {
    companion object {
        fun toNotificationDetail(
            notification: Notification
        ): NotificationDetail = NotificationDetail(
                id = notification.id,
                title = notification.title,
                time = notification.time
        )
    }
}