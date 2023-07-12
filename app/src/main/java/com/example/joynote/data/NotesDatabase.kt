package com.example.joynote.data

import android.content.Context
import androidx.room.*

@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "Note"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}