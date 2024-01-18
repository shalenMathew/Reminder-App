package com.example.reminderapp.feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.feature.domain.model.ReminderNote
import com.example.reminderapp.feature.domain.use_cases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel // used for making factory as we are passing parameters in view model
class ReminderViewModel  @Inject constructor(private val notesUseCases: NotesUseCases): ViewModel() {

    fun insertNote(note:ReminderNote){
        viewModelScope.launch {
           notesUseCases.insertNote.insertNote(note)
        }
    }

   suspend fun insertNoteAndGetId(note: ReminderNote):Long{
        return notesUseCases.insertNote.insertNoteAndGetId(note)
    }

    fun updateNote(note: ReminderNote){
        viewModelScope.launch {
            notesUseCases.updateNote.updateNote(note)
        }
    }

    fun deleteNote(note:ReminderNote){
        viewModelScope.launch {
            notesUseCases.deleteNote(note)
        }
    }

    fun getAllNotes() = notesUseCases.getAllNotes.invoke()

}