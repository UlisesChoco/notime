package com.chocolatada.notime.notification_detail.domain

import com.chocolatada.notime.notification_detail.data.model.NotificationDetail

interface INotificationDetailRepository {
    suspend fun getById(notificationId: Int): NotificationDetail

    suspend fun update(notificationDetail: NotificationDetail)

    suspend fun delete(notificationDetail: NotificationDetail)
}