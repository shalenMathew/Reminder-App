package com.example.reminderapp.feature.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.reminderapp.feature.domain.model.ReminderNote


@Dao
interface ReminderNotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertNote(note: ReminderNote)

//   @Query("SELECT * FROM remindernote WHERE id=:id")
//   suspend fun getNoteById(id:Int): ReminderNote?

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertNoteAndGetId(note: ReminderNote):Long

   @Update
   suspend fun updateNote(note: ReminderNote)

   @Delete
   suspend fun deleteNote(note: ReminderNote)

   @Query("SELECT * FROM remindernote")
   fun getAllNotes(): LiveData<List<ReminderNote>>

}