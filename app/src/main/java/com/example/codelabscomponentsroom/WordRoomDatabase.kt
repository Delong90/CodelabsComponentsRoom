package com.example.codelabscomponentsroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
// Аннотирует класс как базу данных комнаты с таблицей (сущностью) класса Word
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()

                    // Delete all content here.
                    wordDao.deleteAll()

                    // Add sample words.
                    var word = Word("Hello")
                    wordDao.insert(word)
                    word = Word("World!")
                    wordDao.insert(word)

                    // TODO: Add your own words!
                    word = Word("TODO!")
                    wordDao.insert(word)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        // Синглтон предотвращает многократное открытие базы данных на
        // в то же время.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            // если ЭКЗЕМПЛЯР не равен нулю, вернуть его,
            // если есть, то создаем базу данных
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                // возвращаем экземпляр
                instance
            }
        }
    }
}


//8. Добавьте базу данных комнат.
//Что такое база данных номеров **? **
//Room - это уровень базы данных поверх базы данных SQLite.

//Room позаботится о рутинных задачах, с которыми вы раньше справлялись с помощью SQLiteOpenHelper.

//Room использует DAO для отправки запросов к своей базе данных.

//По умолчанию, чтобы избежать низкой производительности пользовательского интерфейса,
// Room не позволяет отправлять запросы в основном потоке. Когда запросы Room возвращаются Flow,
// запросы автоматически выполняются асинхронно в фоновом потоке.

//Room обеспечивает проверку операторов SQLite во время компиляции.


//Внедрить базу данных комнат
//Класс вашей базы данных Room должен быть абстрактным и расширенным RoomDatabase.
// Обычно вам нужен только один экземпляр базы данных Room для всего приложения.

//Пройдемся по коду:
//
//Класс базы данных для Room должен быть abstractи расширятьRoomDatabase.

//Вы аннотируете класс как базу данных Room @Databaseи используете параметры
// аннотации для объявления сущностей, которые принадлежат базе данных, и установки
// номера версии. Каждой сущности соответствует таблица, которая будет создана в базе данных.
// Миграции базы данных выходят за рамки этой кодовой таблицы, поэтому exportSchemaздесь
// установлено значение false, чтобы избежать предупреждения сборки. В реальном приложении
// рассмотрите возможность установки каталога для Room, который будет использоваться для
// экспорта схемы, чтобы вы могли проверить текущую схему в своей системе контроля версий.

//База данных предоставляет DAO с помощью абстрактного метода получения для каждого @Dao.

//Вы определили синглтон , WordRoomDatabase,чтобы предотвратить одновременное открытие
// нескольких экземпляров базы данных.

//getDatabaseвозвращает синглтон. Он создаст базу данных при первом доступе, используя
// построитель базы данных Room, чтобы создать RoomDatabaseобъект в контексте приложения
// из WordRoomDatabaseкласса и присвоить ему имя "word_database".