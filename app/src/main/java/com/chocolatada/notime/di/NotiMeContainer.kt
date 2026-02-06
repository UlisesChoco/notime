package com.chocolatada.notime.di

import android.app.Application
import androidx.room.Room
import com.chocolatada.notime.common.data.dao.NotificationDao
import com.chocolatada.notime.create_notification.data.dao.CreateNotificationDao
import com.chocolatada.notime.create_notification.data.repository.CreateNotificationRepositoryImpl
import com.chocolatada.notime.create_notification.domain.ICreateNotificationRepository
import com.chocolatada.notime.notification_detail.data.dao.NotificationDetailDao
import com.chocolatada.notime.notification_detail.data.repository.NotificationDetailRepositoryImpl
import com.chocolatada.notime.notification_detail.domain.INotificationDetailRepository
import com.chocolatada.notime.notification_list.data.dao.NotificationListDao
import com.chocolatada.notime.notification_list.data.repository.NotificationListRepositoryImpl
import com.chocolatada.notime.notification_list.domain.INotificationListRepository
import com.chocolatada.notime.room.NotiMeDatabase

class NotiMeContainer: Application() {
    private lateinit var database: NotiMeDatabase

    companion object {
        lateinit var notificationDao: NotificationDao

        lateinit var createNotificationRepository: ICreateNotificationRepository
        lateinit var notificationListRepository: INotificationListRepository
        lateinit var notificationDetailRepository: INotificationDetailRepository
    }

    override fun onCreate() {
        super.onCreate()

        this.database = Room.databaseBuilder(
            context = this,
            klass = NotiMeDatabase::class.java,
            name = "noti_me_database"
        ).build()

        notificationDao = database.getNotificationDao()

        val createNotificationDao = CreateNotificationDao(notificationDao)
        val notificationListDao = NotificationListDao(notificationDao)
        val notificationDetailDao = NotificationDetailDao(notificationDao)

        createNotificationRepository = CreateNotificationRepositoryImpl(createNotificationDao)
        notificationListRepository = NotificationListRepositoryImpl(notificationListDao)
        notificationDetailRepository = NotificationDetailRepositoryImpl(notificationDetailDao)
    }
}