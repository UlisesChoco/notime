package com.chocolatada.notime.create_notification.domain

import com.chocolatada.notime.create_notification.data.model.Notification

interface INotificationRepository {
    suspend fun save(notification: Notification)

    suspend fun update(notification: Notification)

    suspend fun delete(notification: Notification)

    suspend fun getAll(): List<Notification>
}