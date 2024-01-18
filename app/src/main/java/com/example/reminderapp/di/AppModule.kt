package com.example.reminderapp.di

import android.app.Application
import androidx.room.Room
import com.example.reminderapp.feature.data.data_source.ReminderNoteDatabase
import com.example.reminderapp.feature.data.data_source.ReminderNotesDao
import com.example.reminderapp.feature.data.repository.ReminderRepositoryImpl
import com.example.reminderapp.feature.domain.repository.ReminderRepository
import com.example.reminderapp.feature.domain.use_cases.DeleteNote
import com.example.reminderapp.feature.domain.use_cases.GetAllNotes
import com.example.reminderapp.feature.domain.use_cases.InsertNote
import com.example.reminderapp.feature.domain.use_cases.NotesUseCases
import com.example.reminderapp.feature.domain.use_cases.UpdateNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun providesReminderDataBase(application: Application):ReminderNoteDatabase{
        return Room.databaseBuilder(application,ReminderNoteDatabase::class.java,"reminder_db").fallbackToDestructiveMigration().build()
    }


    @Singleton
    @Provides
    fun providesDao(db:ReminderNoteDatabase):ReminderNotesDao{
        return db.getDao()  // we will use db object to fetch getDao which have instance of dao which is populated by Room
    }


    @Singleton
    @Provides
    fun providesRepositoryImpl(dao: ReminderNotesDao): ReminderRepository {
        // as we know we cant pass implementation in a parameter instead we need to pass a class that  implements the implementation
        return ReminderRepositoryImpl(dao)
    }


    @Singleton
    @Provides
    fun providesNotesUseCases( reminderRepository: ReminderRepository):NotesUseCases{
        return NotesUseCases(InsertNote(reminderRepository), DeleteNote(reminderRepository),
            UpdateNote(reminderRepository), GetAllNotes(reminderRepository)
        )
    }


}