package com.chocolatada.notime.create_notification.data.dao

import com.chocolatada.notime.common.data.dao.NotificationDao
import com.chocolatada.notime.create_notification.data.model.CreateNotification
import com.chocolatada.notime.create_notification.domain.CreateNotificationMapper
import com.chocolatada.notime.create_notification.domain.ICreateNotificationRepository

class CreateNotificationDao(
    private val notificationDao: NotificationDao
): ICreateNotificationRepository {
    override suspend fun save(createNotification: CreateNotification) {
        val notification = CreateNotificationMapper.toNotification(createNotification)

        notificationDao.save(notification)
    }
}
