package com.chocolatada.notime.create_notification.domain

import com.chocolatada.notime.create_notification.data.model.CreateNotification
import com.chocolatada.notime.create_notification.presentation.CreateNotificationUiState

class CreateNotificationValidator {
    companion object {
        fun validateTime(time: String): Boolean {
            if(time.isEmpty())
                return true

            if(!time.contains(":"))
                return true

            val timeSplit = time.split(":")
            val hoursInt = timeSplit[0].toInt()
            val minutesInt = timeSplit[1].toInt()
            val hoursStr = timeSplit[0]
            val minutesStr = timeSplit[1]

            return hoursInt !in 0..23 ||
                    minutesInt !in 0..59 ||
                    hoursStr.length != 2 ||
                    minutesStr.length != 2 ||
                    timeSplit.size != 2
        }

        fun validateTitle(
            title: String
        ): Boolean = title.isEmpty() || title.length > 20
    }
}