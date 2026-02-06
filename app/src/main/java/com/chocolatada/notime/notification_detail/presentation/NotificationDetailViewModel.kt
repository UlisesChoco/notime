package com.chocolatada.notime.notification_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chocolatada.notime.notification_detail.data.model.NotificationDetail
import com.chocolatada.notime.notification_detail.domain.INotificationDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationDetailViewModel(
    private val notificationDetailRepository: INotificationDetailRepository
): ViewModel() {
    private val _notificationDetail = MutableStateFlow(
        NotificationDetail(
            id = 0,
            title = "",
            time = ""
        )
    )
    val notificationDetail = _notificationDetail.asStateFlow()

    private val _uiState = MutableStateFlow<NotificationDetailUiState>(
        NotificationDetailUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    fun load(notificationId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _notificationDetail.value = notificationDetailRepository.getById(notificationId)
            _uiState.value = NotificationDetailUiState.Idle
        }
    }

    fun onTitleChange(title: String) {
        _notificationDetail.value = _notificationDetail.value.copy(title = title)
    }

    fun onTimeChange(time: String) {
        _notificationDetail.value = _notificationDetail.value.copy(time = time)
    }

    fun update(notificationDetail: NotificationDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = NotificationDetailUiState.Updating
            notificationDetailRepository.update(notificationDetail)
            _uiState.value = NotificationDetailUiState.Updated
        }
    }

    fun delete(notificationDetail: NotificationDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = NotificationDetailUiState.Deleting
            notificationDetailRepository.delete(notificationDetail)
            _uiState.value = NotificationDetailUiState.Deleted
        }
    }
}