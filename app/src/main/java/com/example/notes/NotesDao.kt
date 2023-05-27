package com.example.notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {
    @get:Query("SELECT * FROM notes ORDER BY dayOfWeek")
    val getAllNotes: LiveData<List<Note>>
    // Получение всех заметок из базы данных и упорядочивание их по дню недели.
    // LiveData обеспечивает автоматическое обновление при изменении данных.

    @Insert
    fun insertNote(note: Note)
    // Вставка новой заметки в базу данных.

    @Delete
    fun deleteNote(note: Note)
    // Удаление указанной заметки из базы данных.

    @Query("DELETE FROM notes")
    fun deleteAllNote()
    // Удаление всех заметок из базы данных.
}
