package com.microtears.orange.recyclerview.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SpaceItemDecoration(
    private val space: Int,
    private val spanCount: Int,
    private val isSquare: Boolean = false
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        fun hasSpanSizeLookup(layoutManager: RecyclerView.LayoutManager?): Boolean {
            return when (layoutManager) {
                is GridLayoutManager -> layoutManager.spanSizeLookup != null
                is StaggeredGridLayoutManager -> {
                    true
                }
                else -> false
            }
        }

        fun getsSpanSize(layoutManager: RecyclerView.LayoutManager?, position: Int): Int {
            return when (layoutManager) {
                is GridLayoutManager -> layoutManager.spanSizeLookup.getSpanSize(position)
                is StaggeredGridLayoutManager -> {
                    if ((view.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan) spanCount else 1
                }
                else -> 1
            }
        }

        fun getIndex(repeat: Int, layoutManager: RecyclerView.LayoutManager?): Int {
            var index = 0
            var tmp = 0
            repeat(repeat) {
                val spanSize = getsSpanSize(layoutManager, it)
                tmp += spanSize
                index += tmp / spanCount
                tmp %= spanCount
            }
            return index
        }

        val size = parent.adapter?.itemCount ?: 0
        val layoutManager = parent.layoutManager
        val p = parent.getChildLayoutPosition(view)
        val hasSpanSizeLookup = hasSpanSizeLookup(layoutManager)
        val lineCount = if (hasSpanSizeLookup) {
            getIndex(size, layoutManager)
        } else {
            (size + spanCount / 2) / spanCount
        }
        val lineIndex = if (hasSpanSizeLookup) {
            getIndex(p, layoutManager)
        } else {
            p / spanCount
        }

        if (lineIndex == 0) {
            outRect.top = space
            outRect.bottom = space / 2
        } else if (lineIndex == lineCount - 1) {
            outRect.top = space / 2
            outRect.bottom = space
        } else {
            outRect.top = space / 2
            outRect.bottom = space / 2
        }


        val spanIndex = when {
            view.layoutParams is StaggeredGridLayoutManager.LayoutParams -> (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
            view.layoutParams is GridLayoutManager.LayoutParams -> (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
            else -> 0
        }


        if (isSquare) {
            outRect.left = space / 2
            outRect.right = space / 2
        } else {
            if (spanIndex == 0) {
                outRect.left = space
                outRect.right = space / 2
            } else if (spanIndex == spanCount - 1) {
                outRect.left = space / 2
                outRect.right = space
            } else {
                outRect.left = space / 2
                outRect.right = space / 2
            }
        }
    }

}