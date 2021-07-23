package com.example.codelabscomponentsroom

//Word: - это класс сущности, содержащий одно слово.

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//https://developer.android.com/training/data-storage/room/defining-data


@Entity(tableName = "word_table")
class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)


//Посмотрим, что делают эти аннотации:
//
//@Entity(tableName = "word_table")Каждый @Entity класс представляет собой таблицу SQLite.
// Аннотируйте свое объявление класса, чтобы указать, что это сущность. Вы можете указать
// имя таблицы, если хотите, чтобы оно отличалось от имени класса. Это имя таблицы "word_table".

//@PrimaryKey Каждой сущности нужен первичный ключ. Для простоты каждое слово действует как собственный первичный ключ.

//@ColumnInfo(name = "word")Задает имя столбца в таблице, если вы хотите, чтобы оно отличалось от имени переменной-члена. Это имя столбца «слово».
//Каждое свойство, хранящееся в базе данных, должно быть общедоступным, что является значением Kotlin по умолчанию.