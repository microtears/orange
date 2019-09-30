package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.TimeUnit

fun <S> MutableLiveData<S>.setError(throwable: Throwable) {
    return Observable.setError(this, throwable)
}

fun <S> LiveData<S>.observe(
    lifecycle: LifecycleOwner,
    errorObserver: Observer<Throwable>? = null,
    observer: Observer<S>
) {
    return Observable.observe(this, lifecycle, observer, errorObserver)
}

fun <S, T> LiveData<S>.map(mapFunction: (S) -> T): LiveData<T> {
    return Transform.map(this, mapFunction)
}


fun <S, T> LiveData<S>.mapIndex(mapIndexFunction: (Int, S) -> T): LiveData<T> {
    return Transform.mapIndex(this, mapIndexFunction)
}


fun <S, T> LiveData<S>.switchMap(switchMapFunction: (S) -> LiveData<T>): LiveData<T> {
    return Transform.switchMap(this, switchMapFunction)
}


fun <S> LiveData<S>.distinctUntilChanged(): LiveData<S> {
    return Transform.distinctUntilChanged(this)
}


fun <S, T> LiveData<S>.flatMap(flatMapFunction: (S) -> LiveData<T>): LiveData<T> {
    return Transform.flatMap(this, flatMapFunction)
}


fun <S> LiveData<S>.where(testFunction: (S) -> Boolean): LiveData<S> {
    return Transform.where(this, testFunction)
}


fun <S> LiveData<S>.distinct(): LiveData<S> {
    return Transform.distinct(this)
}


fun <S, K> LiveData<S>.distinct(keys: (S) -> K): LiveData<S> {
    return Transform.distinct(this, keys)
}


fun <S> LiveData<S>.throttleFirst(timeValue: Long, timeUnit: TimeUnit): LiveData<S> {
    return Transform.throttleFirst(this, timeValue, timeUnit)
}


fun <S> LiveData<S>.collect(): LiveData<Collection<S>> {
    return Transform.collect(this)
}


fun <S> LiveData<S>.collect(
    mutableCollection: MutableCollection<S> = mutableListOf(),
    collectFunction: (collection: MutableCollection<S>, S) -> Unit = { collection, it ->
        collection.add(it)
    }
): LiveData<Collection<S>> {
    return Transform.collect(this, mutableCollection, collectFunction)
}


fun <S, T> LiveData<S>.reduce(initValue: T, reduceFunction: (T, S) -> T): LiveData<T> {
    return Transform.reduce(this, initValue, reduceFunction)
}


fun <S> LiveData<S>.count(): LiveData<Int> {
    return Transform.count(this)
}


fun <S, T> LiveData<S>.cast(): LiveData<T> {
    return Transform.cast(this)
}


fun <S> LiveData<S>.timestamp(): LiveData<Pair<S, Long>> {
    return Transform.timestamp(this)
}


fun <S> LiveData<S>.take(size: Int): LiveData<S> {
    return Transform.take(this, size)
}


fun <S> LiveData<S>.takeWhile(testFunction: (S) -> Boolean): LiveData<S> {
    return Transform.takeWhile(this, testFunction)
}


fun <S> LiveData<S>.takeUntil(testFunction: (S) -> Boolean): LiveData<S> {
    return Transform.takeUntil(this, testFunction)
}


fun <S> LiveData<S>.skip(size: Int): LiveData<S> {
    return Transform.skip(this, size)
}


fun <S> LiveData<S>.at(index: Int): LiveData<S> {
    return Transform.at(this, index)
}


fun <S, T> LiveData<S>.type(clazz: Class<T>): LiveData<T> {
    return Transform.type(this, clazz)
}