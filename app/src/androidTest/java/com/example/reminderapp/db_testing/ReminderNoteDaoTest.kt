package com.example.reminderapp.db_testing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.reminderapp.feature.data.data_source.ReminderNoteDatabase
import com.example.reminderapp.feature.data.data_source.ReminderNotesDao
import com.example.reminderapp.feature.domain.model.ReminderNote
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReminderNoteDaoTest {

    @get:Rule
    val instantExecutiveRule=InstantTaskExecutorRule()

    lateinit var database: ReminderNoteDatabase
    lateinit var dao: ReminderNotesDao


    @Before
    fun setUp(){
        database= Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext()
            ,ReminderNoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dao=database.getDao()
    }
    @Test
    fun testingInsert()= runBlocking{

        val reminderNote = ReminderNote(0,"title","desc",false,null,null)

        dao.insertNote(reminderNote) // as we are running this under run blocking the next line will run only when this is completed

        val result =  dao.getAllNotes().getOrAwaitValue() // as we are using live data which is asyncgronus so to to run it as sysnchronus we will
        // do this

        Assert.assertEquals(1,result.size)
        Assert.assertEquals("title",result[0].title)

    }

    @Test
    fun testingUpdate()= runBlocking{

        val note = ReminderNote(0,"title","desc",false,null,null)
        dao.insertNote(note)

        var result = dao.getAllNotes().getOrAwaitValue()

        Assert.assertEquals("title",result[0].title)

        val updatedNote= ReminderNote(result[0].id,"updatedTitle",result[0].desc,result[0].isChecked,null,null)

        dao.updateNote(updatedNote)

        result = dao.getAllNotes().getOrAwaitValue()

        Assert.assertEquals("updatedTitle",result[0].title)

    }

    @Test
    fun deleteNote()= runBlocking{
        val note = ReminderNote(0,"title","desc",false,null,null)
        dao.insertNote(note)

        dao.deleteNote(note)
      val  result = dao.getAllNotes().getOrAwaitValue()
        Assert.assertEquals(0,result.size)
    }


}