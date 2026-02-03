package com.chocolatada.notime.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object NotificationList: NavKey

@Serializable
data object CreateNotification: NavKey

@Serializable
data class NotificationDetail(val notificationId: Int): NavKey
