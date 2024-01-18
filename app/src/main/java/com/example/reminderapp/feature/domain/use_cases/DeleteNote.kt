package com.example.reminderapp.feature.domain.use_cases

import com.example.reminderapp.feature.domain.model.ReminderNote
import com.example.reminderapp.feature.domain.repository.ReminderRepository

class DeleteNote(private val reminderRepository: ReminderRepository) {

   suspend operator fun invoke(note:ReminderNote){
        reminderRepository.delete(note)
    }

}