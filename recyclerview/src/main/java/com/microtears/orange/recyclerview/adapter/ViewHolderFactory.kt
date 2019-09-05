package com.microtears.orange.recyclerview.adapter

import android.view.View
import com.microtears.orange.recyclerview.adapter.ViewHolder

abstract class ViewHolderFactory {
    abstract fun create(type: Int, itemView: View): ViewHolder
}