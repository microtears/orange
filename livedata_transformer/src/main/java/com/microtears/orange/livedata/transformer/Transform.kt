package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.microtears.orange.livedata.transformer.observers.*
import java.util.concurrent.TimeUnit

class Transform {
    companion object {



        @JvmStatic
        fun <S, T> map(source: LiveData<S>, mapFunction: (S) -> T): LiveData<T> {
            return TransformerBase(MapObserver(mapFunction)).transform(source)
        }

        @JvmStatic
        fun <S, T> mapIndex(source: LiveData<S>, mapIndexFunction: (Int, S) -> T): LiveData<T> {
            return TransformerBase(MapIndexObserver(mapIndexFunction)).transform(source)
        }

        @JvmStatic
        fun <S, T> switchMap(
            source: LiveData<S>,
            switchMapFunction: (S) -> LiveData<T>
        ): LiveData<T> {
            return TransformerBase(SwitchMapObserver(switchMapFunction)).transform(source)
        }

        @JvmStatic
        fun <S> distinctUntilChanged(source: LiveData<S>): LiveData<S> {
            return TransformerBase<S, S>(DistinctUntilChangedObserver()).transform(source)
        }

        @JvmStatic
        fun <S, T> flatMap(source: LiveData<S>, flatMapFunction: (S) -> LiveData<T>): LiveData<T> {
            return TransformerBase(FlatMapObserver(flatMapFunction)).transform(source)
        }

        @JvmStatic
        fun <S> where(source: LiveData<S>, testFunction: (S) -> Boolean): LiveData<S> {
            return TransformerBase(WhereObservable(testFunction)).transform(source)
        }

        @JvmStatic
        fun <S> distinct(source: LiveData<S>): LiveData<S> {
            return TransformerBase(DistinctObserver<S, S> { it }).transform(source)
        }

        @JvmStatic
        fun <S, K> distinct(source: LiveData<S>, keys: (S) -> K): LiveData<S> {
            return TransformerBase(DistinctObserver(keys)).transform(source)
        }

        @JvmStatic
        fun <S> throttleFirst(
            source: LiveData<S>,
            timeValue: Long,
            timeUnit: TimeUnit
        ): LiveData<S> {
            return TransformerBase<S, S>(ThrottleFirstObservable(timeValue, timeUnit)).transform(
                source
            )
        }

        @JvmStatic
        fun <S> collect(source: LiveData<S>): LiveData<Collection<S>> {
            return collect(source, mutableListOf())
        }

        @JvmStatic
        fun <S> collect(
            source: LiveData<S>,
            mutableCollection: MutableCollection<S> = mutableListOf(),
            collectFunction: (collection: MutableCollection<S>, S) -> Unit = { collection, it ->
                collection.add(it)
            }
        ): LiveData<Collection<S>> {
            return TransformerBase(CollectObservable(mutableCollection, collectFunction)).transform(
                source
            )
        }

        @JvmStatic
        fun <S, T> reduce(
            source: LiveData<S>,
            initValue: T,
            reduceFunction: (T, S) -> T
        ): LiveData<T> {
            return TransformerBase(ReduceObservable(initValue, reduceFunction)).transform(source)
        }

        @JvmStatic
        fun <S> count(source: LiveData<S>): LiveData<Int> {
            return TransformerBase<S, Int>(CountObservable()).transform(source)
        }

        @JvmStatic
        fun <S, T> cast(source: LiveData<S>): LiveData<T> {
            return TransformerBase(CastObservable<S, T>()).transform(source)
        }

        @JvmStatic
        fun <S> timestamp(source: LiveData<S>): LiveData<Pair<S, Long>> {
            return TransformerBase(TimestampObservable<S>()).transform(source)
        }

        @JvmStatic
        fun <S> take(source: LiveData<S>, size: Int): LiveData<S> {
            return TransformerBase(TakeObservable<S>(size)).transform(source)
        }

        @JvmStatic
        fun <S> takeWhile(source: LiveData<S>, testFunction: (S) -> Boolean): LiveData<S> {
            return TransformerBase(TakeWhileObservable(testFunction)).transform(source)
        }

        @JvmStatic
        fun <S> takeUntil(source: LiveData<S>, testFunction: (S) -> Boolean): LiveData<S> {
            return TransformerBase(TakeUntilObservable(testFunction)).transform(source)
        }


        @JvmStatic
        fun <S> skip(source: LiveData<S>, size: Int): LiveData<S> {
            return TransformerBase(SkipObservable<S>(size)).transform(source)
        }

        @JvmStatic
        fun <S> at(source: LiveData<S>, index: Int): LiveData<S> {
            return TransformerBase(AtObservable<S>(index)).transform(source)
        }

        @JvmStatic
        fun <S, T> type(source: LiveData<S>, clazz: Class<T>): LiveData<T> {
            return TransformerBase<S, T>(TypeObservable(clazz)).transform(source)
        }
    }
}