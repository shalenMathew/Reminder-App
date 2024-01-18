package com.example.reminderapp.feature.presentation

import com.example.reminderapp.feature.domain.model.ReminderNote

interface NoteItemClickListener {
    fun itemClick(note:ReminderNote)
}
