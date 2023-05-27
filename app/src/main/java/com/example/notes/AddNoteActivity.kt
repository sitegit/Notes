package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Инициализация интерфейсных элементов и установка слушателя на кнопку сохранения заметки
        binding.buttonSaveNote.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val description = binding.editTextTextDescription.text.toString().trim()
            val dayOfWeek = binding.spinnerDaysOfWeek.selectedItemPosition
            val radioButtonId = binding.radioGroupPriority.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(radioButtonId)
            val priority = radioButton.text.toString().toInt()

            // Проверка, заполнены ли все необходимые поля
            if (isFilled(title, description)) {
                val note = Note(title, description, dayOfWeek, priority)
                // Создание объекта заметки с полученными данными

                viewModel.insertNote(note)
                // Вызов метода insertNote() ViewModel для вставки заметки в базу данных

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // Переход на главный экран после сохранения заметки
            } else {
                Toast.makeText(this, getString(R.string.check_input), Toast.LENGTH_SHORT).show()
                // Вывод сообщения, если не все поля заполнены
            }
        }
    }

    private fun isFilled(title: String, description: String) =
        title.isNotEmpty() && description.isNotEmpty()
    // Метод для проверки, заполнены ли обязательные поля (заголовок и описание заметки)
}
