@file:Suppress("unused")

package com.microtears.orange.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

private typealias VH = ViewHolder

@Suppress("MemberVisibilityCanBePrivate")
open class MultiTypeAdapter(
    private val factory: ViewHolderFactory,
    val data: MutableList<Pair<Any, Int>> = mutableListOf()
) : RecyclerView.Adapter<VH>() {


    var onItemClick: ((view: View, position: Int) -> Unit)? = null

    var enablePreload = false

    private val preload: Preload<VH> by lazy(LazyThreadSafetyMode.NONE) { Preload(this) }

    var onPreload: (RecyclerView.Adapter<VH>) -> Unit
        get() = preload.onPreload
        set(value) {
            if (!enablePreload) enablePreload = true
            preload.onPreload = value
        }
    var isLoading: Boolean
        get() = preload.isLoading
        private set(value) {
            preload.isLoading = value
        }
    var canLoadMore: Boolean
        get() = preload.canLoadMore
        set(value) {
            preload.canLoadMore = value
        }
    var preloadSize: Int
        get() = preload.size
        set(value) {
            preload.size = value
        }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (enablePreload) {
            preload.unregisterAdapterDataObserver()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return factory.create(
            viewType,
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        ).apply {
            // 此处调用,同一个ViewPool复用ViewHolder会出现数据错乱
            // ViewHolder.layoutPosition返回值不总是正确的
            // onBindViewHolder返回的position才是真实位置
            // 2019-04-19 ViewHolder OnClick Lambda未更新导致的BUG
            itemView.setOnClickListener {
                onItemClick?.invoke(
                    it, /*itemView.getTag(R.id.multi_adapter_view_position) as? Int ?:*/
                    layoutPosition
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    protected open fun getItem(position: Int): Any {
        if (enablePreload) {
            preload.checkLoad(position)
        }
        return data[position].first
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.itemView.setTag(R.id.multi_adapter_view_position, position)
        val itemData = getItem(position)
        holder.onBind(itemData)
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
//        holder.itemView.setTag(R.id.multi_adapter_view_position, position)
        val itemData = getItem(position)
        holder.onBind(itemData, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].second
    }

    //////////////////////////////////////////////////////////////////////////////////////

    fun replaceAll(list: List<Pair<Any, Int>>) {
        data.clear()
        data.addAll(0, list)
        notifyDataSetChanged()
    }

    fun addAll(list: List<Pair<Any, Int>>) {
        val start = data.size
        data.addAll(list)
        notifyItemRangeInserted(start, list.size)
    }

    fun calculateDiff(list: List<Pair<Any, Int>>, cb: DiffUtil.Callback) {
        // todo fix logic
        data.clear()
        data.addAll(list)
        DiffUtil.calculateDiff(cb).dispatchUpdatesTo(this)
    }
}