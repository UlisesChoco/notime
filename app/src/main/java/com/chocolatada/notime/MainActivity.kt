package com.chocolatada.notime

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
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
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(this)

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

    //i need to replace this with a worker
    //it currently works and notifies correctly, but
    //every single time the app gets on this state it launches a new thread qwq
    /*
    override fun onPause() {
        super.onPause()
        val ctx = this
        thread {
            runBlocking {
                var notificationId = 0
                while (true) {
                    val repository = NotiMeContainer.notificationRepository
                    val notifications = repository.getAll()

                    for(notification in notifications) {
                        val today = LocalDate.now()

                        if(notification.dateTriggered.isNotEmpty()) {
                            val dateTriggered: String = notification.dateTriggered
                            val notificationDate = LocalDate.parse(dateTriggered)

                            if(today.equals(notificationDate) || today.isBefore(notificationDate))
                                continue;
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
                                notificationId = notificationId
                            )
                            notificationId++
                            notification.dateTriggered = today.toString()
                            repository.update(notification)
                        }
                    }

                    delay(Duration.ofSeconds(15L))
                }
            }
        }
    }
     */
}