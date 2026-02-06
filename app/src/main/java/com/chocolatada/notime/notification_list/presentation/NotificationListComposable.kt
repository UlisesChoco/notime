package com.chocolatada.notime.notification_list.presentation

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
        INotificationListUiState.Loading -> CircularProgressIndicator()
        INotificationListUiState.Loaded -> MainContent(
            modifier,
            notificationListViewModel,
            onNotificationDetail,
            onCreateNotification
        )
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    notificationListViewModel: NotificationListViewModel,
    onNotificationDetail: (Int) -> Unit,
    onCreateNotification: () -> Unit
) {
    Column(modifier = modifier) {
        DisplayNotificationList(notificationListViewModel, onNotificationDetail)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onCreateNotification() }
        ) {
            Text(text = "Create")
        }
    }
}

@Composable
fun DisplayNotificationList(
    notificationListViewModel: NotificationListViewModel,
    onNotificationDetail: (Int) -> Unit
) {
    val displayMetrics = Resources.getSystem().displayMetrics
    val height = displayMetrics.heightPixels / 2.25f

    val notificationsFlow by notificationListViewModel.notificationsFlow.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.fillMaxWidth().height(height.dp),
    ) {
        if(notificationsFlow.isEmpty()) {
            item {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "No notifications. Please, create one.",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        items(notificationsFlow) { notification ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
                    .height(100.dp)
                    .background(Color.White, RoundedCornerShape(15.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = notification.title,
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                        Text(
                            text = notification.time,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }
                    Button(
                        onClick = { onNotificationDetail(notification.id) }
                    ) {
                        Text(text = "Details")
                    }
                }
            }
        }
    }
}