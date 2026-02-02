package com.chocolatada.notime.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chocolatada.notime.create_notification.domain.ICreateNotificationRepository
import com.chocolatada.notime.create_notification.presentation.CreateNotificationViewModel

class CreateNotificationViewModelFactory(
    private val createNotificationRepository: ICreateNotificationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T = CreateNotificationViewModel(createNotificationRepository) as T
}