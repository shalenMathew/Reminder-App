package com.example.reminderapp.feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminderapp.feature.domain.model.ReminderNote

@Database(entities=[ReminderNote::class], version = 2)
abstract class ReminderNoteDatabase:RoomDatabase() {

abstract fun getDao(): ReminderNotesDao

}