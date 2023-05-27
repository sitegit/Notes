package com.example.notes

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
// Аннотация @Entity указывает, что этот класс представляет таблицу в базе данных, где
// каждый экземпляр класса Note будет представлять строку в таблице.

data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    // Поле id представляет первичный ключ для записей в таблице. Аннотация @PrimaryKey указывает,
    // что это поле является первичным ключом.
    // Параметр autoGenerate = true указывает на автоматическую генерацию значения id базой данных при вставке новой записи.

    var title: String,
    // Поле title представляет заголовок заметки.

    var description: String,
    // Поле description представляет описание заметки.

    var dayOfWeek: Int,
    // Поле dayOfWeek представляет день недели для заметки. Значение 1 соответствует понедельнику, 2 - вторнику и так далее.

    var priority: Int
    // Поле priority представляет приоритет заметки.
) {
    @Ignore
    constructor(title: String, description: String, dayOfWeek: Int, priority: Int) :
            this(0, title, description, dayOfWeek, priority)
    // Второй конструктор класса, помеченный аннотацией @Ignore, используется для создания объекта Note без указания id.
    // Этот конструктор используется, когда создается новая заметка без присвоенного id, который
    // будет автоматически сгенерирован базой данных.
}

