package com.chocolatada.notime.notification_list.domain

import com.chocolatada.notime.common.data.model.Notification
import com.chocolatada.notime.notification_list.data.model.NotificationListItem

class NotificationListMapper {
    companion object {
        fun toNotification(
            notificationListItem: NotificationListItem
        ): Notification = Notification(
            id = notificationListItem.id,
            title = notificationListItem.title,
            time = notificationListItem.time
        )

        fun toNotificationListItem(
            notification: Notification
        ): NotificationListItem = NotificationListItem(
            id = notification.id,
            title = notification.title,
            time = notification.time
        )

        fun toNotificationListItems(
            notifications: List<Notification>
        ): List<NotificationListItem> = notifications.map { notification ->
            toNotificationListItem(notification)
        }
    }
}