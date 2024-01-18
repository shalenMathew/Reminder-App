package com.example.reminderapp.feature.presentation

import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.reminderapp.R
import com.example.reminderapp.databinding.NoteItemBinding
import com.example.reminderapp.feature.domain.model.ReminderNote
import com.example.reminderapp.feature.domain.use_cases.DeleteNote
import java.time.format.DateTimeFormatter

class CustomAdapter(private val context:Context,private val viewModel: ReminderViewModel,private val noteItemClickListener: NoteItemClickListener): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

   private var list:ArrayList<ReminderNote> = ArrayList()

    fun setNotes(list:ArrayList<ReminderNote>){
        this.list.clear()
        this.list=list
        notifyDataSetChanged()
    }

    class ViewHolder(private val context: Context,itemView:View,private val viewModel: ReminderViewModel):RecyclerView.ViewHolder(itemView) {

         private var binding:NoteItemBinding

        private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

        init {
            binding=NoteItemBinding.bind(itemView)
        }

        fun bind(note:ReminderNote){

            binding.titleNoteItem.text=note.title
            binding.timeNoteItem.text = note.dueTime
            if(note.dueTime() != null)
                binding.timeNoteItem.text = timeFormat.format(note.dueTime())
            else
                binding.timeNoteItem.text = ""

            if(note.isChecked == true){
                binding.checkBtnNoteItem.setImageResource(R.drawable.a_checked_24)
                binding.checkBtnNoteItem.setColorFilter(ContextCompat.getColor(context, R.color.green))
                binding.titleNoteItem.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.timeNoteItem.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            }

            binding.checkBtnNoteItem.setOnClickListener(){
                note.isChecked=true
                viewModel.updateNote(note)
                binding.checkBtnNoteItem.setImageResource(R.drawable.a_checked_24)
                binding.checkBtnNoteItem.setColorFilter(ContextCompat.getColor(context, R.color.green))
                binding.titleNoteItem.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.timeNoteItem.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            }



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return ViewHolder(context,view,viewModel)
    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {

        holder.bind(list[position])

        holder.itemView.setOnLongClickListener(){
            deleteDialog(list[position])
            return@setOnLongClickListener true
        }

        holder.itemView.setOnClickListener(){
            noteItemClickListener.itemClick(list[position])
        }

    }

    private fun deleteDialog(note: ReminderNote) {

        val alert = AlertDialog.Builder(context)
        alert.setTitle("Delete Note?")
        alert.setMessage("do u want to delete the note?")

        alert.setPositiveButton("Yes"){
                dialog, which ->
            deleteNote(note)
        }

        alert.setNegativeButton("No",null)
        alert.create().show()

    }

    private fun deleteNote(note: ReminderNote) {
        viewModel.deleteNote(note)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}