package com.chocolatada.notime.notification_list.presentation

sealed interface INotificationListUiState {
    object Loading: INotificationListUiState
    object Loaded: INotificationListUiState
}