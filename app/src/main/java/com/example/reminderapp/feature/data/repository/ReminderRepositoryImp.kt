package com.example.reminderapp.feature.data.repository

import androidx.lifecycle.LiveData
import com.example.reminderapp.feature.data.data_source.ReminderNotesDao
import com.example.reminderapp.feature.domain.model.ReminderNote
import com.example.reminderapp.feature.domain.repository.ReminderRepository

class ReminderRepositoryImpl(private val dao: ReminderNotesDao): ReminderRepository {

    override suspend fun insert(note: ReminderNote) {
        dao.insertNote(note)
    }

    override suspend fun delete(note: ReminderNote) {
       dao.deleteNote(note)
    }

    override  fun getAllNotes(): LiveData<List<ReminderNote>> {
        return dao.getAllNotes()
    }

    override suspend fun update(note: ReminderNote) {
       dao.updateNote(note)
    }

    override suspend fun insertNoteAndGetId(note: ReminderNote): Long {
       return dao.insertNoteAndGetId(note)
    }

//    override suspend fun getNoteById(id: Int): ReminderNote? {
//        return dao.getNoteById(id)
//    }


}