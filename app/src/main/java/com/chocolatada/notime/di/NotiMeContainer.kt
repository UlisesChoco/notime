package com.chocolatada.notime.di

import android.app.Application
import androidx.room.Room
import com.chocolatada.notime.create_notification.data.dao.CreateNotificationDao
import com.chocolatada.notime.create_notification.data.repository.CreateNotificationRepositoryImpl
import com.chocolatada.notime.create_notification.domain.ICreateNotificationRepository
import com.chocolatada.notime.room.NotiMeDatabase

class NotiMeContainer: Application() {
    private lateinit var database: NotiMeDatabase

    companion object {
        lateinit var createNotificationRepository: ICreateNotificationRepository
    }

    override fun onCreate() {
        super.onCreate()

        this.database = Room.databaseBuilder(
            context = this,
            klass = NotiMeDatabase::class.java,
            name = "noti_me_database"
        ).build()

        val notificationDao = database.getNotificationDao()
        val createNotificationDao = CreateNotificationDao(notificationDao)

        createNotificationRepository = CreateNotificationRepositoryImpl(createNotificationDao)
    }
}