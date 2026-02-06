package com.chocolatada.notime.create_notification.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.chocolatada.notime.di.NotiMeContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreateNotification(
    modifier: Modifier = Modifier,
    createNotificationViewModel: CreateNotificationViewModel,
    onBack: () -> Unit
) {
    val uiState by createNotificationViewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        CreateNotificationUiState.Idle -> {
            MainContent(
                modifier,
                createNotificationViewModel,
                onBack
            )
        }
        CreateNotificationUiState.Saving -> {
            MainContent(
                modifier,
                createNotificationViewModel,
                onBack
            ) {
                CircularProgressIndicator()
            }
        }
        CreateNotificationUiState.Saved -> {
            MainContent(
                modifier,
                createNotificationViewModel,
                onBack
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Notification saved successfully",
                    color = Color.Green,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        CreateNotificationUiState.InvalidTitle -> {
            MainContent(
                modifier,
                createNotificationViewModel,
                onBack
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Notification's title cannot be empty or have more than 20 characters",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            }
        }
        CreateNotificationUiState.InvalidTime -> {
            MainContent(
                modifier,
                createNotificationViewModel,
                onBack
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Notification's time should follow the format hh:mm",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    createNotificationViewModel: CreateNotificationViewModel,
    onBack: () -> Unit,
    onSavingOrSaved: @Composable () -> Unit = { }
) {
    val createNotification by createNotificationViewModel.createNotification.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create Notification",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            TextField(
                modifier = Modifier.padding(bottom = 5.dp),
                value = createNotification.title,
                onValueChange = { createNotificationViewModel.onTitleChange(it) },
                singleLine = true,
                shape = CircleShape,
                label = { Text(text = "Notification Title") }
            )

            TextField(
                modifier = Modifier.padding(bottom = 5.dp),
                value = createNotification.time,
                onValueChange = { createNotificationViewModel.onTimeChange(it) },
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
                    onClick = { onBack() }
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
                        createNotificationViewModel.save()
                    }
                ) {
                    Text(text = "Confirm")
                }
            }

            onSavingOrSaved()
        }
    }
}