package com.chocolatada.notime.create_notification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chocolatada.notime.create_notification.data.model.CreateNotification
import com.chocolatada.notime.create_notification.domain.CreateNotificationValidator
import com.chocolatada.notime.create_notification.domain.ICreateNotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateNotificationViewModel(
    private val createNotificationRepository: ICreateNotificationRepository
): ViewModel() {
    private val _createNotification = MutableStateFlow(
            CreateNotification(
                title = "",
                time = ""
            )
        )
    val createNotification = _createNotification.asStateFlow()

    private val _uiState = MutableStateFlow<CreateNotificationUiState>(
        CreateNotificationUiState.Idle
    )
    val uiState = _uiState.asStateFlow()

    fun onTitleChange(title: String) {
        _createNotification.value = _createNotification.value.copy(title = title)
    }

    fun onTimeChange(time: String) {
        _createNotification.value = _createNotification.value.copy(time = time)
    }

    fun save() {
        val isNotValidTitle = CreateNotificationValidator.validateTitle(
            _createNotification.value.title
        )
        val isNotValidTime = CreateNotificationValidator.validateTime(
            _createNotification.value.time
        )

        if(isNotValidTitle) {
            _uiState.value = CreateNotificationUiState.InvalidTitle
            return
        }

        if(isNotValidTime) {
            _uiState.value = CreateNotificationUiState.InvalidTime
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = CreateNotificationUiState.Saving

            createNotificationRepository.save(_createNotification.value)

            _uiState.value = CreateNotificationUiState.Saved

            delay(3000)

            _uiState.value = CreateNotificationUiState.Idle
        }
    }
}