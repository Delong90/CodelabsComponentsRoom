package com.example.codelabscomponentsroom

//WordViewModel: предоставляет методы для доступа к уровню данных и возвращает LiveData,
// чтобы MainActivity мог установить отношения наблюдателя. *

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    // Использование LiveData и кеширование того, что возвращает allWords, имеет несколько преимуществ:
    // - Мы можем поставить наблюдателя на данные (вместо опроса на предмет изменений) и только обновить
    // пользовательский интерфейс при фактическом изменении данных.
    // - Репозиторий полностью отделен от UI через ViewModel.


//    LiveData<List<Word>>: Делает возможным автоматическое обновление
//    компонентов пользовательского интерфейса. Вы можете преобразовать из Flow,
//    в LiveData, позвонив flow.toLiveData().
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    /**
    * Запуск новой сопрограммы для вставки данных неблокирующим способом
    */


    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//10. Создайте ViewModel.
//Что такое ViewModel?
//Его ViewModel роль заключается в предоставлении данных пользовательскому интерфейсу и сохранению изменений конфигурации. A ViewModelдействует как центр связи между репозиторием и пользовательским интерфейсом. Вы также можете использовать ViewModelдля обмена данными между фрагментами. ViewModel является частью библиотеки жизненного цикла .

//
//Для вводного руководства по этой теме см ViewModel Overviewили в ViewModels: Простой пример блога.
//
//Зачем использовать ViewModel?
//A ViewModelхранит данные пользовательского интерфейса вашего приложения с учетом жизненного цикла, сохраняя при этом изменения конфигурации. Разделение данных пользовательского интерфейса своего приложения из ваших Activityи Fragmentклассов позволяет лучше следовать единому принципу ответственности: Ваша деятельность и фрагменты отвечают за составление данных на экран, в то время как ваши ViewModelможет позаботиться о проведении и обработки всех данных , необходимых для пользовательского интерфейса.
//
//LiveData и ViewModel
//LiveData - это наблюдаемый держатель данных - вы можете получать уведомления каждый раз, когда данные меняются. В отличие от Flow, LiveData учитывает жизненный цикл, то есть учитывает жизненный цикл других компонентов, таких как Activity или Fragment. LiveData автоматически останавливает или возобновляет наблюдение в зависимости от жизненного цикла компонента, который отслеживает изменения. Это делает LiveData идеальным компонентом для использования для изменяемых данных, которые пользовательский интерфейс будет использовать или отображать.
//
//ViewModel преобразует данные из репозитория из потока в LiveData и предоставляет пользовательскому интерфейсу список слов как LiveData. Это гарантирует, что каждый раз при изменении данных в базе данных ваш пользовательский интерфейс будет автоматически обновляться.
//
//viewModelScope
//В Kotlin все сопрограммы выполняются внутри CoroutineScope. Область видимости контролирует время жизни сопрограмм посредством своей работы. Когда вы отменяете задание области видимости, она отменяет все сопрограммы, запущенные в этой области.
//
//Библиотека AndroidX lifecycle-viewmodel-ktxдобавляет функцию viewModelScopeрасширения ViewModelкласса, позволяя вам работать с областями.
//
//Чтобы узнать больше о работе с сопрограммами в ViewModel, ознакомьтесь с Шагом 5 раздела « Использование сопрограмм Kotlin в кодовой таблице приложений Android» или «Простыми сопрограммами в Android: viewModelScope» .
//
//Реализуйте ViewModel
//Создайте файл класса Kotlin WordViewModelи добавьте в него этот код:


//Давайте разберем этот код. Здесь у вас есть:
//
//создал класс с именем, WordViewModelкоторый получает в WordRepositoryкачестве параметра и расширяется ViewModel.
// Репозиторий - единственная зависимость, которая нужна ViewModel.
// Если бы потребовались другие классы, они также были бы переданы в конструктор.

//добавлена ​​общедоступная LiveData переменная-член для кеширования списка слов.

//инициализируется LiveDataс allWords потоком из хранилища. Затем вы преобразовали Flow в LiveData, вызвавasLiveData().

//создал insert()метод- оболочку, который вызывает метод репозитория insert(). Таким образом, реализация insert()
// инкапсулируется из пользовательского интерфейса. Мы запускаем новую сопрограмму и вызываем вставку репозитория,
// которая является функцией приостановки. Как уже упоминалось, у ViewModels есть вызываемая область сопрограмм,
// основанная на их жизненном цикле viewModelScope, которую вы здесь будете использовать.

//создал ViewModel и реализован , ViewModelProvider.Factoryкоторый получает в качестве параметра зависимостей ,
// необходимых для создания WordViewModel: WordRepository.

// Используя viewModels и ViewModelProvider.Factory, фреймворк позаботится о жизненном цикле ViewModel.
// Он сохранит изменения конфигурации, и даже если Activity будет воссоздан, вы всегда получите
// правильный экземпляр WordViewModel класса.

//Используя viewModels и ViewModelProvider.Factory, фреймворк позаботится о жизненном цикле ViewModel.
// Он сохранит изменения конфигурации, и даже если Activity будет воссоздан, вы всегда получите правильный
// экземпляр WordViewModel класса.