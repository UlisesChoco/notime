package com.chocolatada.notime.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chocolatada.notime.notification_detail.domain.INotificationDetailRepository
import com.chocolatada.notime.notification_detail.presentation.NotificationDetailViewModel

class NotificationDetailViewModelFactory(
    private val notificationDetailRepository: INotificationDetailRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationDetailViewModel(notificationDetailRepository) as T
    }
}