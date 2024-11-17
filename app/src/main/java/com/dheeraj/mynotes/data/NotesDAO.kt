package com.dheeraj.mynotes.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface NotesDAO {
    @Insert
    suspend fun insertData(data:DataOfNotes)
    @Query("DELETE FROM NotesTable WHERE id = :id")
    suspend fun deleteDataById(id: Int)

    @Query("UPDATE NotesTable SET title =:title1,description=:description1,date=:date1 Where id=:id")
    suspend fun updateData(id:Int,title1:String,description1:String,date1:String)

    @Query("SELECT * FROM NotesTable")
    suspend  fun getList(): List<DataOfNotes>

    @Query("SELECT * FROM NotesTable ORDER BY title DESC")
    suspend fun getDescList(): List<DataOfNotes>
    @Query("SELECT * FROM NotesTable ORDER BY title Asc ")
    suspend fun getAsccList(): List<DataOfNotes>
    @Query("SELECT COUNT(*) FROM NotesTable")
    suspend fun getRowCount(): Int


}
