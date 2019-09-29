package com.microtears.orange.example.fragments

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.microtears.orange.livedata.transformer.*
import kotlin.random.Random

class LiveDataViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    companion object {
        val words = arrayOf("every", "day", "is", "beautiful", "!")
    }

    val liveData = MediatorLiveData<String>()

    val count=liveData.count()

    val realWord=liveData.where{ s ->
        s.all { it.isLetter() }
    }

    val realWordList=realWord.collect()

    val realWordSet=realWord.collect(mutableSetOf())

    val newWord=realWord.distinct()

    fun randomWord() {
        val rand= Random.nextInt(words.size)
        liveData.value= words[rand]
    }

}
