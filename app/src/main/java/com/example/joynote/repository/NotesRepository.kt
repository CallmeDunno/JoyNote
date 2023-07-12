package com.example.joynote.repository

import androidx.lifecycle.LiveData
import com.example.joynote.data.Notes
import com.example.joynote.data.NotesDao

class NotesRepository(private val notesDao: NotesDao) {

    fun getNotesList(): LiveData<List<Notes>> {
        return notesDao.getAllNotes()
    }

    fun insertNote(note: Notes) {
        notesDao.insertNote(note)
    }

    fun deleteNote(id: Int) {
        notesDao.deleteNote(id)
    }
}