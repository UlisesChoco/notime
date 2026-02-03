package com.chocolatada.notime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.chocolatada.notime.create_notification.presentation.CreateNotification
import com.chocolatada.notime.create_notification.presentation.CreateNotificationViewModel
import com.chocolatada.notime.di.NotiMeContainer
import com.chocolatada.notime.di.factory.CreateNotificationViewModelFactory
import com.chocolatada.notime.di.factory.NotificationDetailViewModelFactory
import com.chocolatada.notime.di.factory.NotificationListViewModelFactory
import com.chocolatada.notime.notification_detail.presentation.NotificationDetail
import com.chocolatada.notime.notification_detail.presentation.NotificationDetailViewModel
import com.chocolatada.notime.notification_list.presentation.NotificationList
import com.chocolatada.notime.notification_list.presentation.NotificationListViewModel

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(NotificationList)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<NotificationList> {
                val viewModel = viewModel<NotificationListViewModel>(
                    factory = NotificationListViewModelFactory(
                        NotiMeContainer.notificationListRepository
                    )
                )
                viewModel.load()
                NotificationList(
                    modifier = modifier,
                    notificationListViewModel = viewModel,
                    onNotificationDetail = { notificationId ->
                        backStack.add(NotificationDetail(notificationId))
                    },
                    onCreateNotification = {
                        backStack.add(CreateNotification)
                    }
                )
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
            entry<NotificationDetail> { key ->
                val viewModel = viewModel<NotificationDetailViewModel>(
                    factory = NotificationDetailViewModelFactory(
                        NotiMeContainer.notificationDetailRepository
                    )
                )
                NotificationDetail(
                    modifier,
                    viewModel,
                    key.notificationId
                ) {
                    backStack.removeLastOrNull()
                }
            }
        }
    )
}