package com.chocolatada.notime.util

import com.chocolatada.notime.common.data.model.Notification
import com.chocolatada.notime.create_notification.data.model.CreateNotification

class NotificationMapper {
    companion object {
        fun toNotification(
            createNotification: CreateNotification
        ): Notification = Notification(
            title = createNotification.title,
            time = createNotification.time
        )
    }
}