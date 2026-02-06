package com.chocolatada.notime.create_notification.presentation

sealed interface CreateNotificationUiState {
    object Idle: CreateNotificationUiState

    object Saving: CreateNotificationUiState
    object Saved: CreateNotificationUiState

    object InvalidTitle: CreateNotificationUiState
    object InvalidTime: CreateNotificationUiState
}