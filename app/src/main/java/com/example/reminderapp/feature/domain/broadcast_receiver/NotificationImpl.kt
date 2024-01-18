package com.example.reminderapp.feature.domain.broadcast_receiver

import android.content.Context
import android.content.Intent

interface NotificationImpl {

    fun sendNotification(context: Context, intent: Intent)

}