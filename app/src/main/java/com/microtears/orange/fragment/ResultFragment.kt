@file:Suppress("unused")

package com.microtears.orange.fragment


import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.random.Random

private typealias ResultCallback = (resultCode: Int, data: Intent?) -> Unit

class ResultFragment : Fragment() {

    private val callbacks = SparseArray<ResultCallback>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun startActivityForResult(
        intent: Intent?,
        options: Bundle?,
        callback: (Int, Intent?) -> Unit
    ) {
        val requestCode = makeRequestCode()
        callbacks.put(requestCode, callback)
        startActivityForResult(intent, requestCode, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = callbacks[requestCode]!!
        callbacks.delete(requestCode)
        callback(resultCode, data)
    }

    private fun makeRequestCode(): Int {
        var requestCode: Int
        var count = 0
        do {
            requestCode = Random.nextInt(0x0000FFFF)
            count++
        } while (callbacks.indexOfKey(requestCode) >= 0 && count <= 10)
        return requestCode
    }

}

private const val COM_MICROTEARS_RESULT_TAG = "com.microtears.util.ResultFragment"

fun FragmentActivity.startActivityForResult(
    intent: Intent,
    options: Bundle? = null,
    callback: (resultCode: Int, data: Intent?) -> Unit
) {
    var fragment =
        supportFragmentManager.findFragmentByTag(COM_MICROTEARS_RESULT_TAG) as? ResultFragment
    if (fragment == null) {
        fragment = ResultFragment()
        supportFragmentManager.beginTransaction().add(fragment, COM_MICROTEARS_RESULT_TAG)
            .commitNow()
    }
    fragment.startActivityForResult(intent, options, callback)
}

fun Fragment.startActivityForResult(
    intent: Intent,
    options: Bundle? = null,
    callback: (resultCode: Int, data: Intent?) -> Unit
) {
    var fragment =
        activity!!.supportFragmentManager.findFragmentByTag(COM_MICROTEARS_RESULT_TAG) as? ResultFragment
    if (fragment == null) {
        fragment = ResultFragment()
        activity!!.supportFragmentManager.beginTransaction()
            .add(fragment, COM_MICROTEARS_RESULT_TAG).commitNow()
    }
    fragment.startActivityForResult(intent, options, callback)
}

