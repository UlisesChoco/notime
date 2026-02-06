package com.chocolatada.notime.create_notification.domain

import com.chocolatada.notime.common.data.model.Notification
import com.chocolatada.notime.create_notification.data.model.CreateNotification

class CreateNotificationMapper {
    companion object {
        fun toNotification(
            createNotification: CreateNotification
        ): Notification = Notification(
            title = createNotification.title,
            time = createNotification.time
        )
    }
}