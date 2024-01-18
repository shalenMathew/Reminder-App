package com.example.reminderapp.feature.domain.use_cases

import androidx.lifecycle.LiveData
import com.example.reminderapp.feature.domain.model.ReminderNote
import com.example.reminderapp.feature.domain.repository.ReminderRepository

class GetAllNotes(private val noteRepository: ReminderRepository){

    operator fun invoke():LiveData<List<ReminderNote>>{
        return noteRepository.getAllNotes()
    }
}