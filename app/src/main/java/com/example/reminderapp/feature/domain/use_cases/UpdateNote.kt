package com.example.reminderapp.feature.domain.use_cases

import com.example.reminderapp.feature.domain.model.ReminderNote
import com.example.reminderapp.feature.domain.repository.ReminderRepository

class UpdateNote(private val reminderRepository: ReminderRepository) {

    suspend fun updateNote(note:ReminderNote){
        reminderRepository.update(note)
    }

}