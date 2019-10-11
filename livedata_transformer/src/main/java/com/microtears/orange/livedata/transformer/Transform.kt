package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.microtears.orange.livedata.transformer.observers.*
import java.util.concurrent.TimeUnit

class Transform {
    companion object {

        @JvmStatic
        fun <S> from(collection: Collection<S>): LiveData<S> = MutableLiveData<S>().from(collection)

        @JvmStatic
        fun <S, T> map(source: LiveData<S>, mapFunction: (S) -> T): LiveData<T> = source.map(mapFunction)

        @JvmStatic
        fun <S, T> mapIndex(source: LiveData<S>, mapIndexFunction: (Int, S) -> T): LiveData<T> = source.mapIndex(mapIndexFunction)

        @JvmStatic
        fun <S, T> switchMap(source: LiveData<S>, switchMapFunction: (S) -> LiveData<T>): LiveData<T> = source.switchMap(switchMapFunction)

        @JvmStatic
        fun <S> distinctUntilChanged(source: LiveData<S>): LiveData<S> = source.distinctUntilChanged()

        @JvmStatic
        fun <S, T> flatMap(source: LiveData<S>, flatMapFunction: (S) -> LiveData<T>): LiveData<T> = source.flatMap(flatMapFunction)

        @JvmStatic
        fun <S> where(source: LiveData<S>, testFunction: (S) -> Boolean): LiveData<S> = source.where(testFunction)

        @JvmStatic
        fun <S> distinct(source: LiveData<S>): LiveData<S> = source.distinct()

        @JvmStatic
        fun <S, K> distinct(source: LiveData<S>, keys: (S) -> K): LiveData<S> = source.distinct(keys)

        @JvmStatic
        fun <S> throttleFirst(source: LiveData<S>, timeValue: Long, timeUnit: TimeUnit): LiveData<S> = source.throttleFirst(timeValue, timeUnit)

        @JvmStatic
        fun <S> collect(source: LiveData<S>): LiveData<Collection<S>> = source.collect()

        @JvmStatic
        fun <S> collect(
                source: LiveData<S>,
                mutableCollection: MutableCollection<S> = mutableListOf(),
                collectFunction: (collection: MutableCollection<S>, S) -> Unit = { collection, it ->
                    collection.add(it)
                }
        ): LiveData<Collection<S>> = source.collect(mutableCollection, collectFunction)

        @JvmStatic
        fun <S, T> reduce(
                source: LiveData<S>,
                initValue: T,
                reduceFunction: (T, S) -> T
        ): LiveData<T> = source.reduce(initValue, reduceFunction)

        @JvmStatic
        fun <S> count(source: LiveData<S>): LiveData<Int> = source.count()

        @JvmStatic
        fun <S, T> cast(source: LiveData<S>): LiveData<T> = source.cast()

        @JvmStatic
        fun <S> timestamp(source: LiveData<S>): LiveData<Pair<S, Long>> = source.timestamp()

        @JvmStatic
        fun <S> take(source: LiveData<S>, size: Int): LiveData<S> = source.take(size)

        @JvmStatic
        fun <S> takeWhile(source: LiveData<S>, testFunction: (S) -> Boolean): LiveData<S> = source.takeWhile(testFunction)

        @JvmStatic
        fun <S> takeUntil(source: LiveData<S>, testFunction: (S) -> Boolean): LiveData<S> = source.takeUntil(testFunction)

        @JvmStatic
        fun <S> skip(source: LiveData<S>, size: Int): LiveData<S> = source.skip(size)

        @JvmStatic
        fun <S> at(source: LiveData<S>, index: Int): LiveData<S> = source.at(index)

        @JvmStatic
        fun <S, T> type(source: LiveData<S>, clazz: Class<T>): LiveData<T> = source.type(clazz)

        fun <S> skipNull(source: LiveData<S>): LiveData<S> = source.skipNull()
    }
}