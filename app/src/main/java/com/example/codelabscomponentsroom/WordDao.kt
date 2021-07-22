package com.example.codelabscomponentsroom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}

//Пройдемся по нему:
//
//WordDaoэто интерфейс; DAO должны быть интерфейсами или абстрактными классами.

//@DaoАннотацию идентифицирует его как класс DAO для комнаты.

//suspend fun insert(word: Word): Объявляет функцию приостановки для вставки одного слова.

//@InsertАннотаций это специальный метод DAO аннотация , где вы не должны предоставлять какой -
// либо SQL! (Есть также @Deleteи @Updateаннотации для удаления и обновления строк, но вы не используете их в этом приложении.)

//onConflict = OnConflictStrategy.IGNORE: Выбранная стратегия onConflict игнорирует новое слово,
// если оно точно такое же, как уже было в списке. Чтобы узнать больше о доступных стратегиях конфликтов, ознакомьтесь с документацией .

//suspend fun deleteAll(): Объявляет функцию приостановки для удаления всех слов.

//Нет удобной аннотации для удаления нескольких объектов, поэтому она аннотирована общим @Query.

//@Query("DELETE FROM word_table"): @Queryтребует, чтобы вы предоставили SQL-запрос в качестве
// строкового параметра аннотации, что позволяет выполнять сложные запросы чтения и другие операции.

//fun getAlphabetizedWords(): List<Word>: Метод получения всех слов и возврата Listиз них Words.

//@Query("SELECT * FROM word_table ORDER BY word ASC"): Запрос, который возвращает список слов, отсортированных в порядке возрастания.