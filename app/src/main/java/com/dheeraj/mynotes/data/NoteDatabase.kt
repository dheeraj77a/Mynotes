package com.dheeraj.mynotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataOfNotes::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun notesDAO(): NotesDAO

    companion object {
        private var INSTANCE: NoteDatabase? = null
        fun createDataBase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, NoteDatabase::class.java,
                    "ContactDB"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }

    }
}
