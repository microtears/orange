package com.microtears.orange.view.recyclerview.adapter

import androidx.recyclerview.widget.RecyclerView

class Preload<VH : RecyclerView.ViewHolder>(
    var adapter: RecyclerView.Adapter<VH>,
    var onPreload: ((RecyclerView.Adapter<VH>) -> Unit) = {},
    var isLoading: Boolean = false,
    var canLoadMore: Boolean = true,
    var size: Int = 5
) {
    private var dataObserver: RecyclerView.AdapterDataObserver? = null
    private var itemCount = 0

    fun unregisterAdapterDataObserver() {
        dataObserver?.let { adapter.unregisterAdapterDataObserver(it) }
    }

    private fun doOnPreload() {
        if (dataObserver == null) {
            dataObserver = DataObserver()
            adapter.registerAdapterDataObserver(dataObserver!!)
        }
        isLoading = true
        itemCount = adapter.itemCount
        onPreload(adapter)
    }

    fun checkLoad(current: Int) {
        if (!isLoading && canLoadMore && adapter.itemCount - current <= size)
            doOnPreload()
    }

    inner class DataObserver : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            isLoading = false
        }

        override fun onChanged() {
            super.onChanged()
            if (adapter.itemCount > itemCount)
                isLoading = false
        }
    }
}