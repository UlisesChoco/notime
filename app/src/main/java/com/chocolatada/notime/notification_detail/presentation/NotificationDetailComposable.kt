package com.chocolatada.notime.notification_detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chocolatada.notime.notification_detail.data.model.NotificationDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NotificationDetail(
    modifier: Modifier = Modifier,
    notificationDetailViewModel: NotificationDetailViewModel,
    notificationId: Int,
    onBack: () -> Unit
) {
    val uiState by notificationDetailViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = notificationId) {
        notificationDetailViewModel.load(notificationId)
    }

    when(uiState) {
        NotificationDetailUiState.Idle -> {
            MainContent(
                modifier,
                notificationDetailViewModel,
                onBack
            )
        }
        NotificationDetailUiState.Loading -> {
            CircularProgressIndicator()
        }
        NotificationDetailUiState.Updating -> {
            MainContent(
                modifier,
                notificationDetailViewModel,
                onBack
            ) {
                CircularProgressIndicator()
            }
        }
        NotificationDetailUiState.Updated -> {
            MainContent(
                modifier,
                notificationDetailViewModel,
                onBack
            ) {
                Text(
                    text = "Notification updated successfully",
                    color = Color.Green,
                    fontSize = 10.sp
                )
            }
        }
        NotificationDetailUiState.Deleting -> {
            CircularProgressIndicator()
        }
        NotificationDetailUiState.Deleted -> {
            DeletedContent(modifier, onBack)
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    notificationDetailViewModel: NotificationDetailViewModel,
    onBack: () -> Unit,
    onUpdatingOrUpdated: @Composable () -> Unit = { }
) {
    val notificationDetail by notificationDetailViewModel.notificationDetail.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Notification Detail",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            TextField(
                modifier = Modifier.padding(bottom = 5.dp),
                value = notificationDetail.title,
                onValueChange = { notificationDetailViewModel.onTitleChange(it) },
                singleLine = true,
                shape = CircleShape,
                label = { Text(text = "Notification Title") }
            )

            TextField(
                modifier = Modifier.padding(bottom = 5.dp),
                value = notificationDetail.time,
                onValueChange = { notificationDetailViewModel.onTimeChange(it) },
                singleLine = true,
                shape = CircleShape,
                label = { Text(text = "Notification Time") }
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Red,
                        disabledContentColor = Color.Red
                    ),
                    onClick = {
                        onBack()
                    }
                ) {
                    Text(text = "Cancel")
                }

                Button(
                    colors = ButtonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Green,
                        disabledContentColor = Color.Green
                    ),
                    onClick = {
                        notificationDetailViewModel.update(notificationDetail)
                    }
                ) {
                    Text(text = "Confirm")
                }
            }

            Button(
                colors = ButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Red,
                    disabledContentColor = Color.Red
                ),
                onClick = { notificationDetailViewModel.delete(notificationDetail) }
            ) {
                Text(text = "Delete")
            }

            onUpdatingOrUpdated()
        }
    }
}

@Composable
fun DeletedContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Notification deleted successfully",
            color = Color.Green,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onBack()
            }
        ) {
            Text(text = "Back to Notification List")
        }
    }
}