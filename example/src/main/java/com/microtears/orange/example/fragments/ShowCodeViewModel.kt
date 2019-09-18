package com.microtears.orange.example.fragments

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.microtears.orange.livedata.CoroutineViewModel
import com.microtears.orange.livedata.map
import com.microtears.orange.livedata.mutableLiveDataOf
import com.microtears.orange.util.getString

class ShowCodeViewModel(application: Application) : CoroutineViewModel(application) {

    private val filename: MutableLiveData<String> = mutableLiveDataOf()

    val code = filename.map {
        async { readString(it) }
    }

    fun setup(filename: String) {
        this.filename.value = filename
    }

    private fun readString(filename: String): String {
        return getApplication<Application>().assets.getString(filename)
    }

}
