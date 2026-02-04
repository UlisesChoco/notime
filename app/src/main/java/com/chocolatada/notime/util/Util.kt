package com.chocolatada.notime.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

const val channelId = "1"

enum class HttpErrorEnum(val code: Int) {
    Unauthorized(401)
}

fun createNotificationChannel(ctx: Context) {
    val name = "NotiMe Notifications"
    val descriptionText = "NotiMe Notifications"
    val importance = NotificationManager.IMPORTANCE_DEFAULT

    val channel = NotificationChannel(channelId, name, importance).apply {
        description = descriptionText
    }

    val notificationManager: NotificationManager =
        getSystemService(ctx, NotificationManager::class.java) as NotificationManager

    notificationManager.createNotificationChannel(channel)
}

fun sendNotification(ctx: Context, icon: Int, title: String, content: String, notificationId: Int = 0) {
    val builder = NotificationCompat.Builder(ctx, channelId)
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permission = ActivityCompat
            .checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS)

        if(permission == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat
                .from(ctx)
                .notify(0, builder.build())
        }
    } else {
        NotificationManagerCompat
            .from(ctx)
            .notify(notificationId, builder.build())
    }
}