package com.example.reminderapp.feature.domain.use_cases

data class NotesUseCases(
    val insertNote:InsertNote,
    val deleteNote:DeleteNote,
    val updateNote: UpdateNote,
    val getAllNotes: GetAllNotes
)