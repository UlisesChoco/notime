package com.chocolatada.notime.notification_list.data.repository

import com.chocolatada.notime.notification_list.data.dao.NotificationListDao
import com.chocolatada.notime.notification_list.data.model.NotificationListItem
import com.chocolatada.notime.notification_list.domain.INotificationListRepository

class NotificationListRepositoryImpl(
    private val notificationListDao: NotificationListDao
): INotificationListRepository {
    override suspend fun getAll(): List<NotificationListItem> = notificationListDao.getAll()
}