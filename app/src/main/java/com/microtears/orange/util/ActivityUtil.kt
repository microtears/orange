@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.microtears.orange.util

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment

var Activity.backgroundAlpha: Float
    inline get() = window.attributes.alpha
    set(value) {
        val lp = window.attributes
        lp.alpha = value
        if (value == 1f)
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        else
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window.attributes = lp
    }

fun Activity.setNavigationBarColor(color: Int = Color.TRANSPARENT, applyStatusBar: Boolean = true) {
    if (applyStatusBar)
        window.statusBarColor = color
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    window.navigationBarColor = color
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
}

inline fun Activity.lightStatusBar() {
    var flag = window.decorView.systemUiVisibility
    flag = flag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    window.decorView.systemUiVisibility = flag
}

inline fun Activity.darkStatusBar() {
    var flag = window.decorView.systemUiVisibility
    flag = (flag.inv() xor View.SYSTEM_UI_FLAG_LAYOUT_STABLE.inv()).inv()
    window.decorView.systemUiVisibility = flag
}

fun Activity.backToHome() {
    val intent = Intent(Intent.ACTION_MAIN).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        addCategory(Intent.CATEGORY_HOME)
    }
    startActivity(intent)
}

fun Activity.exitToHome() {
    backToHome()
    android.os.Process.killProcess(android.os.Process.myPid())
}

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Activity> Fragment.startActivity() {
    startActivity(Intent(context, T::class.java))
}

//inline val Activity.contentView: View?
//    get() = findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0)