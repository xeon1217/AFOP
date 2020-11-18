package com.example.afop.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.afop.R
import com.example.afop.ui.activity.LoginActivity
import com.example.afop.ui.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token);
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.data.get("title") // firebase에서 보낸 메세지의 title
        val message = remoteMessage.data.get("message") // firebase에서 보낸 메세지의 내용
        val data = remoteMessage.data.get("data")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("data", data)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = "채널"
            val channel_name = "채널 이름"

            val notificationChannel: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannelMessage = NotificationChannel(
                channel,
                channel_name,
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannelMessage.description = "채널에 대한 설명"
            notificationChannelMessage.enableLights(true)
            notificationChannelMessage.enableVibration(true)
            notificationChannelMessage.setShowBadge(false)
            notificationChannelMessage.vibrationPattern = longArrayOf(1000, 1000)
            notificationChannel.createNotificationChannel(notificationChannelMessage)

            val notificationBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(this, channel)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title) //푸시 알림의 제목
                    .setContentText(message) // 푸시 알림의 내용
                    .setChannelId(channel)
                    .setAutoCancel(true) // 선택시 자동으로 삭제되도록 설정
                    .setContentIntent(pendingIntent) // 알림을 눌렀을 때 실행 할 인텐트 설정
                    .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(9999, notificationBuilder.build())
        } else {
            val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, "")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title) //푸시 알림의 제목
                    .setContentText(message) // 푸시 알림의 내용
                    .setAutoCancel(true) // 선택시 자동으로 삭제되도록 설정
                    .setContentIntent(pendingIntent) // 알림을 눌렀을 때 실행 할 인텐트 설정
                    .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(9999, notificationBuilder.build())
        }
    }
}