package com.microtears.orange.livedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class CoroutineViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    // By default all the coroutines launched in this scope should be using the Main dispatcher
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun <T> async(
        context: CoroutineContext = Dispatchers.IO,
        task: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        return scope.async(context) { task() }
    }

    fun launch(
        context: CoroutineContext = Dispatchers.IO,
        task: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch(context) { task() }
    }
}