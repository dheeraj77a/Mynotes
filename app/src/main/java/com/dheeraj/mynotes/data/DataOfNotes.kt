package com.dheeraj.mynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("NotesTable")
data class DataOfNotes(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val description:String ,
    val date:String
)
