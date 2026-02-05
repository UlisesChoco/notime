package com.chocolatada.notime.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.chocolatada.notime.R
import com.chocolatada.notime.common.data.model.Notification
import com.chocolatada.notime.di.NotiMeContainer
import com.chocolatada.notime.util.sendNotification
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.TimeUnit

data class NotificationWorker(
    val ctx: Context, val workerParams: WorkerParameters
): CoroutineWorker(ctx, workerParams) {
    override suspend fun doWork(): Result {
        rescheduleWorker()

        sendNotifications()

        return Result.success()
    }

    private fun rescheduleWorker() {
        val next = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(1L, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(ctx).enqueueUniqueWork(
            "notification-worker",
            ExistingWorkPolicy.REPLACE,
            next
        )
    }

    private fun sendNotifications() {
        val notifications = NotiMeContainer.notificationDao.getAll()
        for(notification in notifications)
            tryToSendNotification(notification)
    }

    private fun tryToSendNotification(notification: Notification) {
        val today = LocalDate.now()

        if(notification.dateTriggered.isNotEmpty()) {
            val dateTriggered: String = notification.dateTriggered
            val notificationDate = LocalDate.parse(dateTriggered)

            if(today.equals(notificationDate) || today.isBefore(notificationDate))
                return
        }

        val currentHour = LocalTime.now().hour
        val currentMinutes = LocalTime.now().minute
        val currentTime = LocalTime.of(currentHour, currentMinutes)
        val notificationTime = LocalTime.parse(notification.time)

        if(notificationTime.equals(currentTime)) {
            sendNotification(
                ctx = ctx,
                icon = R.drawable.ic_launcher_foreground,
                title = notification.title,
                content = notification.time,
                notificationId = notification.id
            )
            notification.dateTriggered = today.toString()
            NotiMeContainer.notificationDao.update(notification)

            //just for debug purposes d:
            //will remove it later
            Log.d("choco-debug", "[ID - ${notification.id}] Sending notification !")
        }
    }
}
