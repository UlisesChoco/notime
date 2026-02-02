package com.chocolatada.notime.common.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class Notification(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String,
    var dateTriggered: String = "",
    var time: String
)