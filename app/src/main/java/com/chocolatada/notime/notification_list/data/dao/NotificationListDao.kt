package com.chocolatada.notime.notification_list.data.dao

import com.chocolatada.notime.common.data.dao.NotificationDao
import com.chocolatada.notime.notification_list.data.model.NotificationListItem
import com.chocolatada.notime.notification_list.domain.INotificationListRepository
import com.chocolatada.notime.notification_list.domain.NotificationListMapper

class NotificationListDao(
    private val notificationDao: NotificationDao
): INotificationListRepository {
    override suspend fun getAll(): List<NotificationListItem> {
        val notifications = notificationDao.getAll()

        return NotificationListMapper.toNotificationListItems(notifications)
    }
}