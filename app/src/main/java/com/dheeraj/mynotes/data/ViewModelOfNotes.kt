package com.dheeraj.mynotes.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dheeraj.mynotes.repository.NoteRepository
import kotlinx.coroutines.delay


class ViewModelOfNotes: ViewModel() {


    var repositoryInstance:NoteRepository?=null
    private  var _noteList= MutableLiveData<List<DataOfNotes>>()
    var noteList: LiveData<List<DataOfNotes>>? = _noteList
    private  var _countRow=MutableLiveData<Int> ()


    suspend  fun getList(): List<DataOfNotes>? {
        _noteList.value=repositoryInstance?.getNoteList()
        return  repositoryInstance?.getNoteList()
    }

    suspend fun insertNewData(title:String, description:String, date:String){
        repositoryInstance?.insert(title,description,date)
        delay(50)
        getReverseList()

    }

    suspend fun deleteData(id:Int){
        repositoryInstance?.deleteData(id)
        delay(50)
        getReverseList()
    }

    suspend fun editData(id: Int, title:String, description:String, date:String){
        repositoryInstance?.editData(id,title,description,date)
        delay(50)
        getReverseList()
    }

    suspend fun descList() {
        _noteList.value=repositoryInstance?.getDescNoteList()

    }

    suspend fun ascList(){
        _noteList.value=repositoryInstance?.getAscNoteList()
    }
    suspend fun getRowCount(): Int? {
      return repositoryInstance?.getRowCount()
    }
    suspend fun getReverseList(): MutableLiveData<List<DataOfNotes>> {
        _noteList.value=repositoryInstance?.getNoteList()?.reversed()
        Log.e("=================================","$_noteList")
        return _noteList }
    fun start(context: Context){
        repositoryInstance= NoteRepository(context)
    }

}