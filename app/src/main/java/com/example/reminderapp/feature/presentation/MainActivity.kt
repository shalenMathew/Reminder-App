package com.example.reminderapp.feature.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reminderapp.R
import com.example.reminderapp.databinding.ActivityMainBinding
import com.example.reminderapp.feature.data.broadcast_receiver.Notification
import com.example.reminderapp.feature.domain.model.ReminderNote
import com.example.reminderapp.utils.Constant
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),NoteItemClickListener {

    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var recyclerView: RecyclerView

    private lateinit var fb:ExtendedFloatingActionButton

    private lateinit var viewModel: ReminderViewModel

    private lateinit var customAdapter: CustomAdapter

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        if(sharedPreferences.getBoolean("firstTime",true)){
            Toast.makeText(this, "Make sure all permissions are given to get notifications", Toast.LENGTH_LONG).show()

           with(sharedPreferences.edit()){
               putBoolean("firstTime",false)
               apply()
           }
        }

        createChannel()

        fb=activityMainBinding.mainFb

      viewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)

        configureRecycleView()
        observeData()

        fb.setOnClickListener(){
            BottomSheet(this,null).show(supportFragmentManager,"newItemTag")
        }


    }

    private fun createChannel() {
        val name = "Naughty Fication Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel =NotificationChannel(Constant.CHANNEL_ID_1,name,importance)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun observeData() {
        viewModel.getAllNotes().observe(this){notesList->
            val notesArrayList = ArrayList(notesList)
            customAdapter.setNotes(notesArrayList)
        }
    }

    private fun configureRecycleView() {
        recyclerView=activityMainBinding.mainRv
        recyclerView.layoutManager=LinearLayoutManager(this)
         customAdapter = CustomAdapter(this,viewModel,this)
        recyclerView.adapter=customAdapter
    }

    override fun itemClick(note: ReminderNote) {
        BottomSheet(this,note).show(supportFragmentManager,"newItemTag")
    }

}