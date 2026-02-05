package com.chocolatada.notime

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.chocolatada.notime.common.data.model.Notification
import com.chocolatada.notime.di.NotiMeContainer
import com.chocolatada.notime.navigation.Navigation
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import java.time.Duration
import java.time.LocalTime
import kotlin.concurrent.thread
import com.chocolatada.notime.ui.theme.NotimeTheme
import com.chocolatada.notime.util.sendNotification
import com.chocolatada.notime.util.createNotificationChannel
import com.chocolatada.notime.util.launchWorker
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(this)

        launchWorker(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val activity = this
            val permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
            val requestCode = 0
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }

        enableEdgeToEdge()
        setContent {
            NotimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}