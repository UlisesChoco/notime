package com.chocolatada.notime.notification_detail.data.repository

import com.chocolatada.notime.notification_detail.data.dao.NotificationDetailDao
import com.chocolatada.notime.notification_detail.data.model.NotificationDetail
import com.chocolatada.notime.notification_detail.domain.INotificationDetailRepository

class NotificationDetailRepositoryImpl(
    private val notificationDetailDao: NotificationDetailDao
): INotificationDetailRepository {
    override suspend fun getById(
        notificationId: Int
    ): NotificationDetail = notificationDetailDao.getById(notificationId)

    override suspend fun update(notificationDetail: NotificationDetail) {
        notificationDetailDao.update(notificationDetail)
    }

    override suspend fun delete(notificationDetail: NotificationDetail) {
        notificationDetailDao.delete(notificationDetail)
    }
}