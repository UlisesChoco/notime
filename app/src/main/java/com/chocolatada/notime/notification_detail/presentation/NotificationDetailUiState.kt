package com.chocolatada.notime.notification_detail.presentation

sealed interface NotificationDetailUiState {
    object Idle: NotificationDetailUiState

    object Loading: NotificationDetailUiState

    object Updating: NotificationDetailUiState
    object Updated: NotificationDetailUiState

    object Deleting: NotificationDetailUiState
    object Deleted: NotificationDetailUiState
}
