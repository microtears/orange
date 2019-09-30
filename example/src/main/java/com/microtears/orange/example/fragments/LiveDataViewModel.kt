package com.microtears.orange.example.fragments

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.microtears.orange.example.lookValue
import com.microtears.orange.livedata.transformer.*
import kotlin.random.Random

class LiveDataViewModel : ViewModel() {
    companion object {
        val words = arrayOf("every", "day", "is", "beautiful", "!")
    }

    private val word = MediatorLiveData<String>()

    val error = word.map {
        throw Throwable(it)
    }
    val count = word.count().lookValue("count is")

    val realWord = word.where { s ->
        s.all { it.isLetter() }
    }.lookValue("real word is")

    val realWordList = realWord.collect().lookValue("real word list is")

    val realWordSet = realWord.collect(mutableSetOf()).lookValue("real word set is")

    val newWord = realWord.distinct().lookValue("new word is")

    fun randomWord() {
        val rand = Random.nextInt(words.size)
        word.value = words[rand]
    }

}
