@file:Suppress("unused")

package com.microtears.orange.util


import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

val ui: CoroutineContext = Dispatchers.Main
val background: CoroutineContext = Dispatchers.Default
val io: CoroutineContext = Dispatchers.IO

fun <T> executeAsync(
    context: CoroutineContext = io,
    task: suspend CoroutineScope.() -> T
): Deferred<T> {
    return GlobalScope.async(context) { task() }
}

infix fun <T> Deferred<T>.executeSync(uiTask: suspend CoroutineScope.() -> T): Job {
    return GlobalScope.launch(ui) { uiTask() }
}

fun <T> executeSync(context: CoroutineContext = ui, uiTask: suspend CoroutineScope.() -> T): Job {
    return GlobalScope.launch(context) { uiTask() }
}