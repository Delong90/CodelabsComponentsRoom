package com.example.codelabscomponentsroom

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    // Нет необходимости отменять эту область видимости, так как она будет снесена с процессом
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    // Использование by lazy, поэтому база данных и репозиторий создаются только тогда, когда они нужны
    // а не при запуске приложения
    val database by lazy { WordRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}

//Вот что вы сделали:
//
//Создал экземпляр базы данных.

//Создал экземпляр репозитория на основе базы данных DAO.

//Поскольку эти объекты должны создаваться только тогда, когда они впервые нужны,
// а не при запуске приложения, вы используете делегирование свойств Kotlin: by lazy.



//В этом WordRoomDatabaseвы создадите собственную реализацию RoomDatabase.Callback(),
// которая также получит CoroutineScopeпараметр конструктора as. Затем вы переопределяете
// onCreateметод заполнения базы данных.