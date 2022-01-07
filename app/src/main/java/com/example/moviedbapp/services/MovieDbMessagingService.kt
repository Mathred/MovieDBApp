package com.example.moviedbapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.moviedbapp.BuildConfig.APPLICATION_ID
import com.example.moviedbapp.R
import com.example.moviedbapp.ui.MainActivity
import com.example.moviedbapp.utils.Navigator
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MovieDbMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            handleDataMessage(remoteMessage.data.toMap())
        }
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun handleDataMessage(data: Map<String, String>) {
        val title = data.getOrDefault(PUSH_KEY_TITLE, "")
        val message = data.getOrDefault(PUSH_KEY_MESSAGE, "")
        val movieId = data.getOrDefault(PUSH_KEY_MOVIE_ID, "")
        if (title.isNotBlank() && message.isNotBlank()) {
            if (movieId.isNotBlank()) {
                Log.d(TAG, "movieId.isNotBlank $movieId")
                openMovieIntent(movieId)
            } else {
                Log.d(TAG, "movieId.isBlank $title $message")
                showNotification(title, message)
            }
        }
    }

    private fun openMovieIntent(movieId: String) {

        val notificationBuilder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_baseline_local_movies_24)
            setContentTitle("Фильм")
            setContentText("Откройте фильм id$movieId")
            priority = NotificationCompat.PRIORITY_HIGH

            val intent = Intent(applicationContext, MainActivity::class.java).apply {
                putExtra(Navigator.START_ACTION, Navigator.Companion.StartAction.OPEN_MOVIE.name)
                putExtra(Navigator.MOVIE_ID, movieId)
            }
            val stackBuilder = TaskStackBuilder.create(applicationContext)
            stackBuilder.addNextIntent(intent)
            val pendingIntent = stackBuilder.getPendingIntent(
                NOTIFICATION_ID,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            setContentIntent(pendingIntent)
        }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(notificationManager)
            }
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun showNotification(title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_baseline_notifications_24)
            setContentTitle(title)
            setContentText(message)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val name = getString(R.string.default_notification_channel_name)
        val descriptionText = getString(R.string.default_notification_channel_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val TAG = "MovieDbMessagingService"
        const val NOTIFICATION_ID = 101
        const val NOTIFICATION_CHANNEL_ID = "$APPLICATION_ID.channel"
        const val PUSH_KEY_TITLE = "title"
        const val PUSH_KEY_MESSAGE = "message"
        const val PUSH_KEY_MOVIE_ID = "movie_id"
    }
}