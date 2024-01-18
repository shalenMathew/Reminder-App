package com.example.reminderapp.feature.data.broadcast_receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.reminderapp.R
import com.example.reminderapp.feature.domain.broadcast_receiver.NotificationImpl
import com.example.reminderapp.feature.presentation.MainActivity
import com.example.reminderapp.utils.Constant

class Notification:BroadcastReceiver(),NotificationImpl {


    override fun onReceive(context: Context, intent: Intent) {

        sendNotification(context,intent)

    }

    override fun sendNotification(context: Context, intent: Intent) {

        val noteId = intent.getIntExtra(Constant.note_Id,0)
        val title = intent.getStringExtra(Constant.title_Extra)
        val message = intent.getStringExtra(Constant.message_Extra)

        val int= Intent(context,MainActivity::class.java)
        int.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pi = PendingIntent.getActivity(context,noteId,int,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context,Constant.CHANNEL_ID_1)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        nm.notify(noteId,notification)
    }


}