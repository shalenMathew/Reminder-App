package com.example.reminderapp.feature.domain.repository

import androidx.lifecycle.LiveData
import com.example.reminderapp.feature.domain.model.ReminderNote

interface ReminderRepository {

    // here gonna define some  function we need , and fill the fun in using Impl class

    // this way we created an abstraction , now we can use this interface as a parameter and call its function in usecases without
    // exposing the Dao directly to the Usecases

    // every layer should be independent of each other

    suspend fun insert(note: ReminderNote)

    suspend fun delete(note: ReminderNote)

     fun getAllNotes(): LiveData<List<ReminderNote>>

     suspend fun update(note: ReminderNote)

     suspend fun insertNoteAndGetId(note: ReminderNote):Long

//    suspend   fun getNoteById(id:Int): ReminderNote?
}