package com.microtears.orange.livedata.transformer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

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

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        val liveData = MutableLiveData<Int>()
        liveData.value = 3
        val result = liveData.await()
        assert(result == 3)
    }
}
