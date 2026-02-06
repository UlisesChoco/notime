package com.chocolatada.notime.create_notification.domain

import com.chocolatada.notime.create_notification.data.model.CreateNotification

interface ICreateNotificationRepository {
    suspend fun save(createNotification: CreateNotification)
}