package com.chocolatada.notime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.chocolatada.notime.create_notification.presentation.CreateNotification
import com.chocolatada.notime.create_notification.presentation.CreateNotificationViewModel
import com.chocolatada.notime.di.NotiMeContainer
import com.chocolatada.notime.di.factory.CreateNotificationViewModelFactory

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val backStack = remember { mutableListOf<NavKey>(CreateNotification) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<NotificationList> {

            }
            entry<CreateNotification> {
                val viewModel = viewModel<CreateNotificationViewModel>(
                    factory = CreateNotificationViewModelFactory(
                        NotiMeContainer.createNotificationRepository
                    )
                )
                CreateNotification(modifier, viewModel) {
                    backStack.removeLastOrNull()
                }
            }
        }
    )
}