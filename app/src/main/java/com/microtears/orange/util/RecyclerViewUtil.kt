package com.microtears.orange.util

import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.LayoutAnimationController
import android.view.animation.LinearInterpolator
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

inline val RecyclerView.gridLayoutManager: GridLayoutManager
    get() = layoutManager as GridLayoutManager

inline val RecyclerView.linearLayoutManager: LinearLayoutManager
    get() = layoutManager as LinearLayoutManager

inline val RecyclerView.staggeredGridLayoutManager: StaggeredGridLayoutManager
    get() = layoutManager as StaggeredGridLayoutManager


fun RecyclerView.setItemAnimation(
    @AnimRes id: Int, delay: Float = 0.1f,
    interpolator: Interpolator = LinearInterpolator(),
    order: Int = LayoutAnimationController.ORDER_NORMAL
) {
    val animation = AnimationUtils.loadAnimation(context, id)
    val layoutAnimController = LayoutAnimationController(animation)
    layoutAnimController.interpolator = interpolator
    layoutAnimController.order = order
    layoutAnimController.delay = delay
    layoutAnimation = layoutAnimController
}


fun RecyclerView.getFirstItemPosition(): Int {
    if (layoutManager is LinearLayoutManager) {
        return linearLayoutManager.findFirstVisibleItemPosition()
    }
    if (layoutManager is StaggeredGridLayoutManager) {
        val arr = IntArray(staggeredGridLayoutManager.spanCount)
        staggeredGridLayoutManager.findFirstVisibleItemPositions(arr)
        return arr.min() ?: -1
    }
    if (layoutManager is GridLayoutManager) {
        return gridLayoutManager.findFirstVisibleItemPosition()
    }
    return -1
}


fun RecyclerView.getLastItemPosition(): Int {
    if (layoutManager is LinearLayoutManager)
        return linearLayoutManager.findLastVisibleItemPosition()
    if (layoutManager is StaggeredGridLayoutManager) {
        val arr = IntArray((layoutManager as StaggeredGridLayoutManager).spanCount)
        staggeredGridLayoutManager.findLastVisibleItemPositions(arr)
        return arr.max() ?: -1
    }
    if (layoutManager is GridLayoutManager) {
        return gridLayoutManager.findLastVisibleItemPosition()
    }
    return -1
}