package com.chocolatada.notime.notification_detail.data.dao

import com.chocolatada.notime.common.data.dao.NotificationDao
import com.chocolatada.notime.notification_detail.data.model.NotificationDetail
import com.chocolatada.notime.notification_detail.domain.INotificationDetailRepository
import com.chocolatada.notime.notification_detail.domain.NotificationDetailMapper

class NotificationDetailDao(
    private val notificationDao: NotificationDao
): INotificationDetailRepository {
    override suspend fun getById(notificationId: Int): NotificationDetail {
        val notification = notificationDao.getById(notificationId)

        return NotificationDetailMapper.toNotificationDetail(notification)
    }

    override suspend fun update(notificationDetail: NotificationDetail) {
        val notification = notificationDao.getById(notificationDetail.id)

        notification.title = notificationDetail.title
        notification.time = notificationDetail.time

        notificationDao.update(notification)
    }

    override suspend fun delete(notificationDetail: NotificationDetail) {
        val notification = notificationDao.getById(notificationDetail.id)

        notificationDao.delete(notification)
    }
}