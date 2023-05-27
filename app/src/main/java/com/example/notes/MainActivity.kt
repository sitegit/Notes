package com.example.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesAdapter
    private lateinit var viewModel: MainViewModel

    // Создаем список для заметок
    private val notes = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализируем ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Загружаем данные из базы данных
        getData()

        // Инициализируем адаптер для списка заметок
        adapter = NotesAdapter(notes, object : NotesAdapter.OnNoteClickListener {
            // Обрабатываем клик на заметке
            override fun onNoteClick(position: Int) {
                val noteId = adapter.getNotes()[position].id.toString()
                Toast.makeText(this@MainActivity, noteId, Toast.LENGTH_SHORT).show()
            }

            // Обрабатываем долгий клик на заметке
            override fun onNoteLongClick(position: Int) {
                removeCard(position)
            }
        })

        // Настраиваем RecyclerView для списка заметок
        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        // Обрабатываем клик на кнопке добавления заметки
        binding.buttonAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        // Настраиваем swipe для удаления заметки
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // Не используется в данном примере, поэтому оставляем TODO
                TODO("Not yet implemented")
            }

            // Обрабатываем swipe для удаления заметки
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeCard(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)
    }

    // Удаляем заметку и обновляем список
    private fun removeCard(position: Int) {
        val note = adapter.getNotes()[position]
        viewModel.deleteNote(note)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        // Получаем заметки из базы данных через ViewModel
        val notesFromDB = viewModel.getNotes()
        notesFromDB.observe(this) { notesFromLiveData ->
            adapter.setNotes(notesFromLiveData) // Обновляем список заметок в адаптере
        }
    }
}
