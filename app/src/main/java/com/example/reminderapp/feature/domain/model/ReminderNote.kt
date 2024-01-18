package com.example.reminderapp.feature.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

@Entity
data class ReminderNote(
 @PrimaryKey
   val id:Int?=null,
   var title:String,
    var desc:String?,
    var isChecked:Boolean?,
    var dueTime:String?,
    var completedDate:String?
)
{


    private fun completedDate(): LocalDate? = if (completedDate == null) null else LocalDate.parse(completedDate, dateFormatter) // parsing string to a format
    fun dueTime(): LocalTime? = if (dueTime == null) null else LocalTime.parse(dueTime, timeFormatter)


    companion object {
        // here what this below code does is that it is a formatter which will be used to format  a string to a specific format
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }

}


class InvalidException(message:String):Exception(message)


