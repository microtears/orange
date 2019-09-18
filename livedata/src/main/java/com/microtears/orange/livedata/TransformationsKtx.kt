package com.microtears.orange.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

object TransformationsKtx {

    @JvmStatic
    fun <X, Y> addSource(
        source: LiveData<X>,
        result: MediatorLiveData<Y> = MediatorLiveData(),
        action: (result: MediatorLiveData<Y>, it: X) -> Unit
    ): MediatorLiveData<Y> {
        result.addSource(source) {
            action(result, it)
        }
        return result
    }

    @JvmStatic
    fun <X, Y, Z> combine(
        source1: LiveData<X>,
        source2: LiveData<Y>,
        action: (value1: X, value2: Y) -> Z
    ): MediatorLiveData<Z> {
        val outResult = MediatorLiveData<Z>()
        addSource(source1, outResult) { result, it ->
            source2.value?.let { elseValue ->
                result.value = action(it, elseValue)
            }
        }
        addSource(source2, outResult) { result, it ->
            source1.value?.let { elseValue ->
                result.value = action(elseValue, it)
            }
        }
        return outResult
    }

    @JvmStatic
    fun <X> merge(list: List<LiveData<X>>): LiveData<X> {
        if (list.isEmpty()) throw RuntimeException("list must not empty")
        if (list.size == 1) return list[0]
        val resultOut = MediatorLiveData<X>()
        list.forEach { liveData ->
            addSource(liveData, resultOut) { result, it ->
                result.value = it
            }
        }
        return resultOut
    }

    @JvmStatic
    fun <X> mergeList(list: List<LiveData<List<X>>>): LiveData<List<X>> {
        if (list.isEmpty()) throw RuntimeException("list must not empty")
        if (list.size == 1) return list[0]
        var first = list[0]
        for (i in 1 until list.size) {
            first = mergeList(first, list[i])
        }
        return first
    }

    @JvmStatic
    fun <X> mergeList(first: LiveData<List<X>>, second: LiveData<List<X>>): LiveData<List<X>> {
        val resultOut = MediatorLiveData<List<X>>()
        addSource(first, resultOut) { result, it ->
            val list = mutableListOf<X>()
            list.addAll(it)
            list.addAll(second.value ?: emptyList())
            result.value = list
        }
        addSource(second, resultOut) { result, it ->
            val list = mutableListOf<X>()
            list.addAll(first.value ?: emptyList())
            list.addAll(it)
            result.value = list
        }
        return resultOut
    }

    @JvmStatic
    fun <X> skipNull(source: LiveData<X>): LiveData<X> {
        return addSource(source){ result, it->
            if(it!=null)
                result.value=it
        }
    }

    @JvmStatic
    fun <X, Y> map(source: LiveData<X>, action: (X) -> Y): LiveData<Y> {
        return Transformations.map(skipNull(source), action)
    }

    @JvmStatic
    fun <X, Y> mapIndex(source: LiveData<X>, action: (index: Int, it: X) -> Y): LiveData<Y> {
        var index = -1
        return map(source) { action(++index, it) }
    }

    @JvmStatic
    fun <X, Y> switchMap(source: LiveData<X>, action: (X) -> LiveData<Y>): LiveData<Y> {
        return Transformations.switchMap(skipNull(source), action)
    }

    @JvmStatic
    fun <X> distinctUntilChanged(source: LiveData<X>): LiveData<X> {
        return Transformations.distinctUntilChanged(skipNull(source))
    }

    @JvmStatic
    fun <X, Y> flatMap(source: LiveData<X>, action: (X) -> LiveData<Y>): LiveData<Y> {
        return addSource(source) { result, it ->
            result.addSource(action(it)) { flat ->
                result.value = flat
            }
        }
    }

    @JvmStatic
    fun <X> from(collection: Collection<X>): LiveData<X> {
        val result = MutableLiveData<X>()
        collection.forEach {
            result.value = it
        }
        return result
    }

    @JvmStatic
    fun interval(timeValue: Long, timeUnit: TimeUnit): LiveData<Int> {
        return object : LiveData<Int>() {
            private var job: Job? = null
            private var offset = 0
            override fun onActive() {
                super.onActive()
                job = GlobalScope.launch(Dispatchers.Default) {
                    while (isActive) {
                        delay(timeUnit.toMillis(timeValue))
                        value = offset
                        offset++
                    }
                }
            }

            override fun onInactive() {
                super.onInactive()
                job?.cancel()
            }
        }
    }

    @JvmStatic
    fun <X> filter(source: LiveData<X>, action: (X) -> Boolean): LiveData<X> {
        return addSource(source) { result, it ->
            if (!action(it))
                result.value = it
        }
    }

    @JvmStatic
    fun <X> distinct(source: LiveData<X>): LiveData<X> {
        return distinct(source) { it }
    }

    @JvmStatic
    fun <X, Y> distinct(source: LiveData<X>, action: (X) -> Y): LiveData<X> {
        val set = WeakHashSet<Y>()//mutableSetOf<Y>()
        return addSource(source) { result, it ->
            val key = action(it)
            if (!set.contains(key)) {
                set.add(key)
                result.value = it
            }
        }
    }

    @JvmStatic
    fun <X> throttleFirst(source: LiveData<X>, timeValue: Long, timeUnit: TimeUnit): LiveData<X> {
        var previousTimeMillis = -1L
        return addSource(source) { result, it ->
            val currentTimeMillis = System.currentTimeMillis()
            if (currentTimeMillis >= previousTimeMillis + timeUnit.toMillis(timeValue)) {
                previousTimeMillis = currentTimeMillis
                result.value = it
            }
        }
    }

    @JvmStatic
    fun <X> collect(
        source: LiveData<X>,
        list: MutableList<X> = mutableListOf(),
        action: (collection: MutableList<X>, X) -> List<X> = { collection, it ->
            collection.apply {
                add(it)
            }
        }
    ): LiveData<List<X>> {
        return map(source) {
            action(list, it)
        }
    }

    @JvmStatic
    fun <X, Y, Z> reduce(source: LiveData<X>, init: Y, action: (Y, X) -> Z): LiveData<Z> {
        return map(source) {
            action(init, it)
        }
    }

    @JvmStatic
    fun <X> count(source: LiveData<X>): LiveData<Int> {
        return reduce(source, 0) { acc, _ ->
            acc + 1
        }
    }

    @JvmStatic
    fun <X> onChanged(source: LiveData<X>, action: (X) -> Unit): LiveData<X> {
        return map(source) {
            action(it)
            it
        }
    }

    @JvmStatic
    fun <X, Y> cast(source: LiveData<X>, clazz: Class<Y>): LiveData<Y> {
        return map(source) {
            it as Y
        }
    }

    @JvmStatic
    fun <X, Y> groupBy(source: LiveData<X>, action: (X) -> Y): LiveData<Pair<Y, X>> {
        return map(source) {
            action(it) to it
        }
    }

    @JvmStatic
    fun <X> timestamp(source: LiveData<X>): LiveData<Pair<X, Long>> {
        return map(source) {
            it to System.currentTimeMillis()
        }
    }

    @JvmStatic
    fun <X> take(source: LiveData<X>, size: Int): LiveData<X> {
        var count = 0
        return addSource(source) { result, it ->
            if (++count < size) {
                result.value = it
            }
        }
    }

    @JvmStatic
    fun <X> takeWhile(source: LiveData<X>, action: (X) -> Boolean): LiveData<X> {
        return addSource(source) { result, it ->
            if (action(it)) {
                result.value = it
            } else {
                result.removeSource(source)
            }
        }
    }

    @JvmStatic
    fun <X> skip(source: LiveData<X>, size: Int): LiveData<X> {
        var count = 0
        return addSource(source) { result, it ->
            if (++count >= size) {
                result.value = it
            }
        }
    }

    @JvmStatic
    fun <X> skipUntil(source: LiveData<X>, action: (X) -> Boolean): LiveData<X> {
        var checked = false
        return addSource(source) { result, it ->
            if (checked || action(it)) {
                checked = true
                result.value = it
            }
        }
    }

    @JvmStatic
    fun <X> at(source: LiveData<X>, index: Int): LiveData<X> {
        var count = 0
        return addSource(source) { result, it ->
            if (count == index) {
                result.value = it
            }
            count++
        }
    }

    @JvmStatic
    fun <Y> type(source: LiveData<Any?>, clazz: Class<Y>): LiveData<Y> {
        return addSource(source) { result, it ->
            if (clazz.isInstance(it)) {
                result.value = it as Y
            }
        }
    }
}