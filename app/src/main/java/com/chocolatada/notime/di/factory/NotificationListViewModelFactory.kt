package com.chocolatada.notime.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chocolatada.notime.notification_list.domain.INotificationListRepository
import com.chocolatada.notime.notification_list.presentation.NotificationListViewModel

class NotificationListViewModelFactory(
    private val notificationListRepository: INotificationListRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationListViewModel(notificationListRepository) as T
    }
}