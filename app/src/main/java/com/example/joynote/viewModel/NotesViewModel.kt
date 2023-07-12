package com.example.joynote.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.joynote.data.Notes
import com.example.joynote.data.NotesDatabase
import com.example.joynote.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private var repo : NotesRepository
    private lateinit var list : LiveData<List<Notes>>

    init {
        val dao = NotesDatabase.getDatabase(application).noteDao()
        repo = NotesRepository(dao)
    }

    fun getAllNotes(): LiveData<List<Notes>> {
        viewModelScope.launch {
            val def = async {
                return@async repo.getNotesList()
            }
            list = def.await()
        }
        return list
    }

    fun insertNote(note: Notes) {
        viewModelScope.launch(Dispatchers.IO){
            repo.insertNote(note)
        }
    }

    fun deleteNote(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNote(id)
        }
    }
}