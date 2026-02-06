package com.chocolatada.notime.create_notification.data.repository

import com.chocolatada.notime.create_notification.data.dao.CreateNotificationDao
import com.chocolatada.notime.create_notification.data.model.CreateNotification
import com.chocolatada.notime.create_notification.domain.ICreateNotificationRepository

class CreateNotificationRepositoryImpl(
    private val dao: CreateNotificationDao
): ICreateNotificationRepository {
    override suspend fun save(createNotification: CreateNotification) {
        dao.save(createNotification)
    }
}