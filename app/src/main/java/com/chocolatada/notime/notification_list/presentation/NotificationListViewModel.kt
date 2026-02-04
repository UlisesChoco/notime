package com.chocolatada.notime.notification_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chocolatada.notime.notification_list.data.model.NotificationListItem
import com.chocolatada.notime.notification_list.domain.INotificationListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationListViewModel(
    private val notificationListRepository: INotificationListRepository
): ViewModel() {
    private val _notificationsFlow = MutableStateFlow(
        emptyList<NotificationListItem>()
    )
    val notificationsFlow = _notificationsFlow.asStateFlow()

    private val _uiState = MutableStateFlow<INotificationListUiState>(
        INotificationListUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = INotificationListUiState.Loading
            _notificationsFlow.value = notificationListRepository.getAll()
            _uiState.value = INotificationListUiState.Loaded
        }
    }
}