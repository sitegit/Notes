package com.example.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*Эта строка является аннотацией @Database, которая применяется к классу NotesDatabase. Аннотация предоставляет информацию о базе данных Room, которая будет создана на основе этого класса. Вот объяснение аргументов аннотации:

entities = [Note::class]: Этот аргумент указывает на список классов сущностей, которые будут использоваться в базе данных. В данном случае у нас только одна сущность - класс Note.
version = 1: Этот аргумент указывает на версию базы данных. Если вы вносите изменения в структуру базы данных, вам нужно увеличивать версию, чтобы Room мог обновить базу данных при следующем запуске приложения.
exportSchema = false: Этот аргумент указывает, должна ли Room генерировать файл схемы базы данных. Значение false означает, что файл схемы не будет создан. Обычно это используется во время разработки, чтобы избежать ненужного сохранения схемы базы данных.*/
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    companion object {
        private var database: NotesDatabase? = null
        private const val DB_NAME = "notes2.db"
        private val LOCK = Any()

        // Функция getInstance создает и возвращает экземпляр базы данных NotesDatabase
        fun getInstance(context: Context): NotesDatabase {
            synchronized(LOCK) {
                if (database == null) {
                    // Построение базы данных Room с использованием контекста приложения, класса базы данных и имени базы данных
                    database = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, DB_NAME)
                        .build()
                }
            }
            return database!!
        }
    }

    abstract fun notesDao(): NotesDao
    // Абстрактная функция, которая возвращает объект NotesDao для взаимодействия с базой данных.
}

