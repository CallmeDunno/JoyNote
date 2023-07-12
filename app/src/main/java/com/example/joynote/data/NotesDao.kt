package com.example.joynote.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {
    @Query("SELECT * FROM Note")
    fun getAllNotes() : LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(notes: Notes)

    @Query("DELETE FROM Note WHERE id = :id")
    fun deleteNote(id : Int)
}

/**
 * No need to update method because when inserted if it conflicts it will be replaced
 * */