package com.example.codelabscomponentsroom

//DAO: сопоставляет вызовы методов с запросами к базе данных, чтобы,
// когда Репозиторий вызывает такой метод, как getAlphabetizedWords()Room,
// может выполняться .SELECT * FROM word_table ORDER BY word ASC

//DAO может предоставлять suspend запросы для одноразовых запросов и
// Flow запросов - когда вы хотите получать уведомления об изменениях в базе данных.

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}



//Что такое DAO?
//В DAO (объект доступа к данным) вы указываете запросы SQL и связываете их с вызовами методов.
// Компилятор проверяет SQL и генерирует запросы из удобных аннотаций для общих запросов, таких
// как @Insert. Room использует DAO для создания чистого API для вашего кода.
//
//DAO должен быть интерфейсом или абстрактным классом.
//
//По умолчанию все запросы должны выполняться в отдельном потоке.
//
//В комнате есть поддержка сопрограмм Kotlin . Это позволяет аннотировать ваши запросы suspend модификатором,
//а затем вызывать их из сопрограммы или из другой функции приостановки.

//Пройдемся по нему:
//
//WordDao это интерфейс; DAO должны быть интерфейсами или абстрактными классами.

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