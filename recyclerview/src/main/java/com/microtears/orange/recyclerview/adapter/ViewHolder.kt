package com.microtears.orange.recyclerview.adapter

import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun <T> onBind(data: T)

    open fun <T> onBind(data: T, payloads: MutableList<Any>) {
        onBind(data)
    }

    private val views: SparseArray<View> by lazy(LazyThreadSafetyMode.NONE) { SparseArray<View>() }

    @Suppress("UNCHECKED_CAST")
    fun <V : View> view(@IdRes viewId: Int): V {
        if (itemView.id == viewId)
            return itemView as V
        var view: View? = views.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as V
    }

    fun transitionName(@IdRes viewId: Int, transitionName: String) {
        view<View>(viewId).transitionName = transitionName
    }

    fun transitionName(@IdRes viewId: Int): String? {
        return view<View>(viewId).transitionName
    }

    fun text(@IdRes viewId: Int, text: CharSequence) {
        view<TextView>(viewId).text = text
    }

    fun text(@IdRes viewId: Int, text: () -> CharSequence) {
        view<TextView>(viewId).text = text()
    }

    fun text(@IdRes viewId: Int): CharSequence? {
        return view<TextView>(viewId).text
    }

    fun check(@IdRes viewId: Int, isChecked: Boolean) {
        view<CheckBox>(viewId).isChecked = isChecked
    }

    fun check(@IdRes viewId: Int): Boolean {
        return view<CheckBox>(viewId).isChecked
    }


    fun visible(@IdRes viewId: Int, isVisible: Boolean = true) {
        if (isVisible)
            view<View>(viewId).visibility = View.VISIBLE
        else
            view<View>(viewId).visibility = View.INVISIBLE
    }

    fun gone(@IdRes viewId: Int, isGone: Boolean = true) {
        if (isGone)
            view<View>(viewId).visibility = View.GONE
        else
            view<View>(viewId).visibility = View.VISIBLE
    }

    fun onCheckedChange(@IdRes viewId: Int, block: ((button: CompoundButton, isChecked: Boolean) -> Unit)?) {
        view<CheckBox>(viewId).setOnCheckedChangeListener(block)
    }

    fun onClick(@IdRes viewId: Int, block: (view: View) -> Unit) {
        view<View>(viewId).setOnClickListener(block)
    }

    fun backgroundColor(@IdRes viewId: Int, @ColorInt color: Int) {
        view<View>(viewId).setBackgroundColor(color)
    }

    fun background(@IdRes viewId: Int, @DrawableRes resId: Int) {
        view<View>(viewId).setBackgroundResource(resId)
    }

    fun background(@IdRes viewId: Int, drawable: Drawable) {
        view<View>(viewId).background = drawable
    }

    fun tag(@IdRes viewId: Int, tag: Any?) {
        view<View>(viewId).tag = tag
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> tag(@IdRes viewId: Int): R? {
        return view<View>(viewId).tag as? R
    }

    fun tag(@IdRes viewId: Int, @IdRes tagKeyId: Int, tag: Any?) {
        view<View>(viewId).setTag(tagKeyId, tag)
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> tag(@IdRes viewId: Int, @IdRes tagKeyId: Int): R? {
        return view<View>(viewId).getTag(tagKeyId) as? R
    }

    fun imageResource(@IdRes viewId: Int, @DrawableRes resId: Int) {
        view<ImageView>(viewId).setImageResource(resId)
    }

    fun alpha(@IdRes viewId: Int): Float {
        return view<View>(viewId).alpha
    }

    fun alpha(@IdRes viewId: Int, alpha: Float) {
        view<View>(viewId).alpha = alpha
    }

    fun scaleX(@IdRes viewId: Int): Float {
        return view<View>(viewId).scaleX
    }

    fun scaleX(@IdRes viewId: Int, value: Float) {
        view<View>(viewId).scaleX = value
    }

    fun scaleY(@IdRes viewId: Int): Float {
        return view<View>(viewId).scaleY
    }

    fun scaleY(@IdRes viewId: Int, value: Float) {
        view<View>(viewId).scaleY = value
    }

    fun animate(@IdRes viewId: Int): ViewPropertyAnimator {
        return view<View>(viewId).animate()
    }

    fun progress(@IdRes viewId: Int): Int {
        return view<ProgressBar>(viewId).progress
    }

    fun progress(@IdRes viewId: Int, value: Int) {
        view<ProgressBar>(viewId).progress = value
    }
}