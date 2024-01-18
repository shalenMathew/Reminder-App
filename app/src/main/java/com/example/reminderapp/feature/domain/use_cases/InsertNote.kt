package com.example.reminderapp.feature.domain.use_cases

import com.example.reminderapp.feature.domain.model.InvalidException
import com.example.reminderapp.feature.domain.model.ReminderNote
import com.example.reminderapp.feature.domain.repository.ReminderRepository
import kotlin.jvm.Throws

class InsertNote(private val reminderRepository: ReminderRepository) {


    @Throws(InvalidException::class)
    suspend fun insertNote(note: ReminderNote){

        if (note.title.isBlank()){
            throw InvalidException("The title is empty")
        }

        reminderRepository.insert(note)
    }

    @Throws(InvalidException::class)
    suspend fun insertNoteAndGetId(note: ReminderNote):Long{

        if (note.title.isBlank()){
            throw InvalidException("The title is empty")
        }

        return reminderRepository.insertNoteAndGetId(note)

    }


}