package com.dheeraj.mynotes.repository

import android.content.Context
import android.util.Log
import com.dheeraj.mynotes.data.DataOfNotes
import com.dheeraj.mynotes.data.NoteDatabase
import com.dheeraj.mynotes.data.NotesDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteRepository(context: Context) {
    var notesDAO: NotesDAO
    var list: List<DataOfNotes>? = null

    init {
        val database = NoteDatabase.createDataBase(context)
        notesDAO = database.notesDAO()
    }

    suspend fun getNoteList(): List<DataOfNotes>? {
        list = notesDAO.getList()
        return list
    }
    suspend fun getDescNoteList(): List<DataOfNotes>? {
        list = notesDAO.getDescList()
        return list
    }
    suspend fun getAscNoteList(): List<DataOfNotes>? {
        list = notesDAO.getAsccList()
        return list
    }
    suspend fun getRowCount(): Int{

        return notesDAO.getRowCount()
    }
    fun insert( title1: String, description1: String, date1: String) {
        Log.e("this", "Data is insert")
        CoroutineScope(Dispatchers.Main).launch {
            notesDAO?.insertData(
                DataOfNotes(
                    title = title1,
                    description = description1,
                    date = date1
                )
            )
        }

    }

    fun deleteData(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            notesDAO?.deleteDataById(id)
        }

    }
    fun editData(id: Int,title:String,description:String,date:String) {
        CoroutineScope(Dispatchers.Main).launch {
            notesDAO?.updateData(id,title,description,date)
        }

    }
}
