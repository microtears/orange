@file:Suppress("unused")

package com.microtears.orange.util


import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

private val exceptionHandler: CoroutineContext =
    CoroutineExceptionHandler { coroutineContext, throwable ->
    }
val ui: CoroutineContext = Dispatchers.Main //+ exceptionHandler
val background: CoroutineContext = Dispatchers.Default //+ exceptionHandler
val io: CoroutineContext = Dispatchers.IO //+ exceptionHandler

fun <T> executeAsync(
    context: CoroutineContext = io,
    task: suspend CoroutineScope.() -> T
): Deferred<T> {
    return GlobalScope.async(context) { task() }
}

infix fun <T> Deferred<T>.executeSync(uiTask: suspend CoroutineScope.() -> T): Job {
    return GlobalScope.launch(ui) { uiTask() }
}