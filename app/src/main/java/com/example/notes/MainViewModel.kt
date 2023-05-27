@file:Suppress("DEPRECATION")

package com.example.notes

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = NotesDatabase.getInstance(getApplication())
    private val notes: LiveData<List<Note>> = database.notesDao().getAllNotes

    // Метод для получения заметок
    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun insertNote(note: Note) {
        InsertTask().execute(note)
        // Выполнение асинхронной задачи для вставки заметки в базу данных
    }

    fun deleteNote(note: Note) {
        DeleteTask().execute(note)
        // Выполнение асинхронной задачи для удаления заметки из базы данных
    }

    /*fun deleteAllNotes() {
        DeleteAllTask().execute()
    }*/

    @SuppressLint("StaticFieldLeak")
    private inner class InsertTask : AsyncTask<Note, Void, Void>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg notes: Note): Void? {
            if (notes.isNotEmpty()) {
                database.notesDao().insertNote(notes[0])
                // Вставка заметки в базу данных через Data Access Object (DAO)
            }
            return null
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class DeleteTask : AsyncTask<Note, Void, Void>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg notes: Note): Void? {
            if (notes.isNotEmpty()) {
                database.notesDao().deleteNote(notes[0])
                // Удаление заметки из базы данных через Data Access Object (DAO)
            }
            return null
        }
    }

    /*private inner class DeleteAllTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg notes: Void): Void? {
            database.notesDao().deleteAllNote()
            // Удаление всех заметок из базы данных через Data Access Object (DAO)
            return null
        }
    }*/
}
