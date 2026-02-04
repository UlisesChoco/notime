package com.chocolatada.notime.notification_list.domain

import com.chocolatada.notime.notification_list.data.model.NotificationListItem

interface INotificationListRepository {
    suspend fun getAll(): List<NotificationListItem>
}