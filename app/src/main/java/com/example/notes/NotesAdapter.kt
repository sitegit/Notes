package com.example.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.NoteItemBinding

class NotesAdapter(
    // Конструктор адаптера принимает список заметок и слушатель кликов на заметках
    private var notes: List<Note>,
    private var onNoteClickListener: OnNoteClickListener
) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    // Интерфейс для слушателя кликов на заметках
    interface OnNoteClickListener {
        fun onNoteClick(position: Int)
        fun onNoteLongClick(position: Int)
    }

    // Создание нового ViewHolder'а. В этом методе мы получаем макет заметки, создаем и возвращаем ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        // Создаем объект привязки для макета заметки с помощью LayoutInflater
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Создаем новый ViewHolder, передавая ему объект привязки и слушатель кликов на заметках
        return NotesViewHolder(binding, onNoteClickListener)
    }

    // Получение количества заметок в списке
    override fun getItemCount() = notes.size

    // Заполнение содержимого заметки. В этом методе мы получаем заметку из списка, используя ее позицию,
    // и заполняем поля ViewHolder'а данными из заметки
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note: Note = notes[position]
        holder.textViewTitle.text = note.title
        holder.textViewDescription.text = note.description
        holder.textViewDayOfWeek.text = getDayAsString(note.dayOfWeek + 1)

        // Устанавливаем фоновый цвет заметки в зависимости от ее приоритета
        val colorId: Int = when (note.priority) {
            1 -> ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_light)
            2 -> ContextCompat.getColor(holder.itemView.context, android.R.color.holo_orange_light)
            else -> ContextCompat.getColor(
                holder.itemView.context,
                android.R.color.holo_green_light
            )
        }
        holder.textViewTitle.setBackgroundColor(colorId)
    }

    // Метод для получения дня недели в виде строки на основе позиции. Позиция 1 соответствует
    // "Понедельник", 2 - "Вторник", и так далее.
    private fun getDayAsString(position: Int): String {
        return when (position) {
            1 -> "Понедельник"
            2 -> "Вторник"
            3 -> "Среда"
            4 -> "Четверг"
            5 -> "Пятница"
            6 -> "Суббота"
            else -> "Воскресенье"
        }
    }
    // Метод для установки списка заметок. Принимает список заметок в качестве параметра.
    // Если переданный список равен null, то используется пустой список.
    // Затем вызывается метод notifyDataSetChanged(), чтобы обновить данные в адаптере и обновить отображение списка.
    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>?) {
        this.notes = notes.orEmpty()
        notifyDataSetChanged()
    }

    // Метод для получения списка заметок. Возвращает текущий список заметок, который содержится в адаптере.
    fun getNotes(): List<Note> {
        return notes
    }

    // ViewHolder хранит ссылки на элементы интерфейса заметки, чтобы не приходилось каждый раз искать их по ID
    class NotesViewHolder(
        binding: NoteItemBinding,
        private val onNoteClickListener: OnNoteClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        // Ссылки на элементы интерфейса заметки
        val textViewTitle: TextView = binding.textViewTitle
        val textViewDescription: TextView = binding.textViewDescription
        val textViewDayOfWeek: TextView = binding.textViewDayOfWeek

        init {
            // Устанавливаем слушатель кликов на заметку
            itemView.setOnClickListener {
                onNoteClickListener.onNoteClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                onNoteClickListener.onNoteLongClick(adapterPosition)
                true
            }
        }
    }
}

