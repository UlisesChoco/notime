package com.chocolatada.notime.notification_list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NotificationList(
    modifier: Modifier = Modifier,
    notificationListViewModel: NotificationListViewModel,
    onNotificationDetail: (Int) -> Unit,
    onCreateNotification: () -> Unit
) {
    val uiState by notificationListViewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        INotificationListUiState.Loading -> {
            CircularProgressIndicator()
        }
        INotificationListUiState.Loaded -> {
            MainContent(
                modifier,
                notificationListViewModel,
                onNotificationDetail,
                onCreateNotification
            )
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    notificationListViewModel: NotificationListViewModel,
    onNotificationDetail: (Int) -> Unit,
    onCreateNotification: () -> Unit
) {
    val notificationsFlow by notificationListViewModel.notificationsFlow.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onCreateNotification() }
            ) {
                Text(text = "Create")
            }

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                if(notificationsFlow.isEmpty()) {
                    item {
                        Text(
                            text = "No notifications. Please, create one.",
                            fontSize = 15.sp
                        )
                    }
                }
                items(notificationsFlow) { notification ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            text = "${notification.title} - ${notification.time}"
                        )
                        Button(
                            onClick = { onNotificationDetail(notification.id) }
                        ) {
                            Text(text = "See details")
                        }
                    }
                }
            }
        }
    }
}