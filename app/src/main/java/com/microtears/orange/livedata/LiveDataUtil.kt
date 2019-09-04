package com.microtears.orange.livedata

import androidx.lifecycle.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@Suppress("UNCHECKED_CAST")
@Throws(InterruptedException::class)
fun <T> LiveData<T>.await(timeoutMillis: Long = Long.MAX_VALUE): T? {
    var data: Any? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@await.removeObserver(this)
        }
    }
    this.observeForever(observer)
    if (timeoutMillis == Long.MAX_VALUE) {
        latch.await()
    } else {
        latch.await(timeoutMillis, TimeUnit.MILLISECONDS)
    }
    return data as? T
}

fun <T> mutableLiveDataOf(default: T? = null): MutableLiveData<T> {
    return default?.let { MutableLiveData(default) } ?: MutableLiveData()
}

fun <T> mediatorLiveDataOf(default: T? = null): MediatorLiveData<T> {
    return default?.let {
        MediatorLiveData<T>().apply { value = it }
    } ?: MediatorLiveData()
}


fun <T> LiveData<T>.observe(owner: LifecycleOwner) {
    observe(owner, Observer { })
}

fun <X> MutableLiveData<X>.changed(action: (X) -> Unit) {
    action(value!!)
    val changed = value!!
    value = changed
}

fun <X> MutableLiveData<X>.postChanged(action: (X) -> Unit) {
    action(value!!)
    val changed = value!!
    postValue(changed)
}

fun <X, Y> LiveData<X>.addSource(
    result: MediatorLiveData<Y> = MediatorLiveData(),
    action: (result: MediatorLiveData<Y>, it: X) -> Unit
): MediatorLiveData<Y> {
    return TransformationsKtx.addSource(this, result, action)
}

fun <X> List<LiveData<X>>.merge(): LiveData<X> {
    return TransformationsKtx.merge(this)
}

fun <X> List<LiveData<List<X>>>.mergeList(): LiveData<List<X>> {
    return TransformationsKtx.mergeList(this)
}

fun <X, Y> LiveData<X>.map(action: (X) -> Y): LiveData<Y> {
    return TransformationsKtx.map(this, action)
}

fun <X, Y> LiveData<X>.mapIndex(action: (index: Int, it: X) -> Y): LiveData<Y> {
    return TransformationsKtx.mapIndex(this, action)
}

fun <X, Y> LiveData<X>.switchMap(action: (X) -> LiveData<Y>): LiveData<Y> {
    return TransformationsKtx.switchMap(this, action)
}

fun <X> LiveData<X>.distinctUntilChanged(): LiveData<X> {
    return TransformationsKtx.distinctUntilChanged(this)
}

fun <X, Y> LiveData<X>.flatMap(action: (X) -> LiveData<Y>): LiveData<Y> {
    return TransformationsKtx.flatMap(this, action)
}

fun <X> Collection<X>.toLiveData(): LiveData<X> {
    return TransformationsKtx.from(this)
}

fun <X> LiveData<X>.filter(action: (X) -> Boolean): LiveData<X> {
    return TransformationsKtx.filter(this, action)
}

fun <X> LiveData<X>.distinct(): LiveData<X> {
    return TransformationsKtx.distinct(this) { it }
}

fun <X, Y> LiveData<X>.distinct(action: (X) -> Y): LiveData<X> {
    return TransformationsKtx.distinct(this, action)
}

fun <X> LiveData<X>.throttleFirst(timeValue: Long, timeUnit: TimeUnit): LiveData<X> {
    return TransformationsKtx.throttleFirst(this, timeValue, timeUnit)
}

fun <X> LiveData<X>.collect(
    list: MutableList<X> = mutableListOf(),
    action: (collection: MutableList<X>, X) -> List<X> = { collection, it ->
        collection.apply {
            add(
                it
            )
        }
    }
): LiveData<List<X>> {
    return TransformationsKtx.collect(this, list, action)
}

fun <X, Y, Z> LiveData<X>.reduce(init: Y, action: (Y, X) -> Z): LiveData<Z> {
    return TransformationsKtx.reduce(this, init, action)
}

fun <X> LiveData<X>.count(): LiveData<Int> {
    return TransformationsKtx.count(this)
}

fun <X> LiveData<X>.onChanged(action: (X) -> Unit): LiveData<X> {
    return TransformationsKtx.onChanged(this, action)
}

fun <X, Y> LiveData<X>.cast(clazz: Class<Y>): LiveData<Y> {
    return TransformationsKtx.cast(this, clazz)
}

fun <X, Y> LiveData<X>.groupBy(action: (X) -> Y): LiveData<Pair<Y, X>> {
    return TransformationsKtx.groupBy(this, action)
}

fun <X> LiveData<X>.timestamp(): LiveData<Pair<X, Long>> {
    return TransformationsKtx.timestamp(this)
}

fun <X> LiveData<X>.take(size: Int): LiveData<X> {
    return TransformationsKtx.take(this, size)
}

fun <X> LiveData<X>.takeWhile(action: (X) -> Boolean): LiveData<X> {
    return TransformationsKtx.takeWhile(this, action)
}

fun <X> LiveData<X>.skip(size: Int): LiveData<X> {
    return TransformationsKtx.skip(this, size)
}

fun <X> LiveData<X>.skipUntil(action: (X) -> Boolean): LiveData<X> {
    return TransformationsKtx.skipUntil(this, action)
}

fun <X> LiveData<X>.at(index: Int): LiveData<X> {
    return TransformationsKtx.at(this, index)
}
