@file:Suppress("unused", "NAME_SHADOWING")

package com.microtears.orange.fragment


import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.random.Random

private typealias PermissionCallback = (permissions: Array<out String>, grantResults: IntArray) -> Unit

class PermissionFragment : Fragment() {
    private val callbacks =
        SparseArray<PermissionCallback>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    fun requestPermissions(
        permissions: Array<out String>,
        callback: PermissionCallback
    ) {
        val requestCode = makeRequestCode()
        callbacks.put(requestCode, callback)
        requestPermissions(permissions, requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val callback = callbacks[requestCode]
        callbacks.delete(requestCode)
        callback(permissions, grantResults)
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

private const val COM_MICROTEARS_PERMISSION_TAG = "com.microtears.util.PermissionFragment"

private fun findFragment(context: Any, isActivity: Boolean): PermissionFragment {
    if (isActivity) {
        context as FragmentActivity
        var fragment =
            context.supportFragmentManager.findFragmentByTag(COM_MICROTEARS_PERMISSION_TAG) as? PermissionFragment
        if (fragment == null) {
            fragment = PermissionFragment()
            context.supportFragmentManager.beginTransaction()
                .add(fragment, COM_MICROTEARS_PERMISSION_TAG).commitNow()
        }
        return fragment
    } else {
        context as Fragment
        var fragment =
            context.activity!!.supportFragmentManager.findFragmentByTag(
                COM_MICROTEARS_PERMISSION_TAG
            ) as? PermissionFragment
        if (fragment == null) {
            fragment = PermissionFragment()
            context.activity!!.supportFragmentManager.beginTransaction()
                .add(fragment, COM_MICROTEARS_PERMISSION_TAG)
                .commitNow()
        }
        return fragment
    }
}


fun FragmentActivity.requestPermissions(
    vararg permissions: String,
    callback: PermissionCallback
) {
    findFragment(this, true).requestPermissions(permissions, callback)
}

fun FragmentActivity.requestPermissions(
    vararg permissions: String,
    callback: (result: Boolean) -> Unit
) {
    findFragment(
        this,
        true
    ).requestPermissions(permissions) { _: Array<out String>, grantResults: IntArray ->
        callback(grantResults.none { it == PackageManager.PERMISSION_DENIED })
    }
}


fun FragmentActivity.requestEachPermissions(
    vararg permissions: String,
    callback: (permission: String, result: Boolean) -> Unit
) {
    findFragment(
        this,
        true
    ).requestPermissions(permissions) { permissions: Array<out String>, grantResults: IntArray ->
        grantResults.forEachIndexed { index, i ->
            callback(permissions[index], i == PackageManager.PERMISSION_GRANTED)
        }
    }
}

fun Fragment.requestPermissions(
    vararg permissions: String,
    callback: PermissionCallback
) {
    findFragment(this, false).requestPermissions(permissions, callback)
}

fun Fragment.requestPermissions(vararg permissions: String, callback: (result: Boolean) -> Unit) {
    findFragment(
        this,
        false
    ).requestPermissions(permissions) { _: Array<out String>, grantResults: IntArray ->
        callback(grantResults.none { it == PackageManager.PERMISSION_DENIED })
    }
}

fun Fragment.requestEachPermissions(
    vararg permissions: String,
    callback: (permission: String, result: Boolean) -> Unit
) {
    findFragment(
        this,
        false
    ).requestPermissions(permissions) { permissions: Array<out String>, grantResults: IntArray ->
        grantResults.forEachIndexed { index, i ->
            callback(permissions[index], i == PackageManager.PERMISSION_GRANTED)
        }
    }
}