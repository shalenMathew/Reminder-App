package com.example.reminderapp.feature.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.reminderapp.databinding.FragmentNewTaskSheetBinding
import com.example.reminderapp.feature.data.broadcast_receiver.Notification
import com.example.reminderapp.feature.domain.broadcast_receiver.NotificationImpl
import com.example.reminderapp.feature.domain.model.ReminderNote
import com.example.reminderapp.utils.Constant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.properties.Delegates

class BottomSheet(private val context:Context,private var note:ReminderNote?):BottomSheetDialogFragment(){


    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var viewModel: ReminderViewModel
    private var dueTime: LocalTime? = null
    private var noteId:Long=-1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        viewModel= ViewModelProvider(activity)[ReminderViewModel::class.java]

        if (note!=null){
            val editable = Editable.Factory.getInstance()

            binding.name.text = editable.newEditable(note!!.title)
            binding.desc.text = editable.newEditable(note!!.desc)

            if (note!!.dueTime()!=null){
                binding.timePickerButton.text=editable.newEditable(note!!.dueTime)
                dueTime = note!!.dueTime() // putting parsed time in this variable
                updateTimeButtonText()
            }
        }

        binding.timePickerButton.setOnClickListener(){
            openTimePicker()
        }

        binding.saveButton.setOnClickListener(){
            saveButton()
        }
    }

    private fun openTimePicker() {

        if (dueTime==null){
            dueTime=LocalTime.now()
        }

        val listener = TimePickerDialog.OnTimeSetListener{_,selectedHour,selectedMin->
            dueTime = LocalTime.of(selectedHour,selectedMin)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity,listener,dueTime!!.hour,dueTime!!.minute,true)
        dialog.setTitle("Time:")
        dialog.show()

    }

    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }

    private fun saveButton() {


        if(binding.name.text!!.isNotBlank()){

            val name = binding.name.text.toString()
            val desc = binding.desc.text.toString()
            val dueTimeString = if(dueTime!=null) ReminderNote.timeFormatter.format(dueTime) else null

            if(note==null){
                val newNote = ReminderNote(title = name, desc = desc, isChecked = null, dueTime = dueTimeString, completedDate = null)

                lifecycleScope.launch {
                    // here trying to get id of the note so running a coroutine so that then the code wont move further until we get our id
                    noteId = withContext(Dispatchers.IO){// Dispatchers.IO is used when read or write operation on disk or network calls
                        viewModel.insertNoteAndGetId(newNote)
                    }
                    // the below code will not run until the above coroutine is finised
                    note=newNote

                    if (dueTime!=null){
                        scheduleNotification()
                    }
                }

            }else{
                note!!.title=name
                note!!.desc=desc
                note!!.dueTime=dueTimeString

                viewModel.updateNote(note!!)

                if(dueTime!=null) {
                    scheduleNotification()
                }

            }

            binding.name.setText("")
            binding.desc.setText("")
            dismiss()

        }else{
            Toast.makeText(context, "Title cannot be null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scheduleNotification(){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context,Notification::class.java)


        intent.putExtra(Constant.title_Extra, note!!.title)
        intent.putExtra(Constant.note_Id,noteId)

        if (note!!.desc!=null) {
            intent.putExtra(Constant.message_Extra,note!!.desc)
        }

        val pi = PendingIntent.getBroadcast(context, noteId.toInt(),intent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val triggerTime = LocalDateTime.now().withHour(dueTime?.hour ?: 0).withMinute(dueTime?.minute ?: 0)
        val zonedTriggerTime = ZonedDateTime.of(triggerTime, ZoneId.systemDefault())

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, zonedTriggerTime.toInstant().toEpochMilli(), pi!!)

    }

}