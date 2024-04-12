package com.example.demoapplication.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


open class BaseViewHolder<T : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var itemBinding: T? = null

    init {
        itemBinding = DataBindingUtil.bind(itemView)
    }
}