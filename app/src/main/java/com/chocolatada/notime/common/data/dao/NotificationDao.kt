package com.chocolatada.notime.common.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chocolatada.notime.common.data.model.Notification

@Dao
interface NotificationDao {
    @Insert
    fun save(notification: Notification)

    @Update
    fun update(notification: Notification)

    @Delete
    fun delete(notification: Notification)

    @Query("SELECT * FROM notification")
    fun getAll(): List<Notification>
}