package com.chocolatada.notime.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chocolatada.notime.common.data.dao.NotificationDao
import com.chocolatada.notime.common.data.model.Notification

@Database(
    version = 1,
    entities = [Notification::class]
)
abstract class NotiMeDatabase : RoomDatabase() {
    abstract fun getNotificationDao(): NotificationDao
}