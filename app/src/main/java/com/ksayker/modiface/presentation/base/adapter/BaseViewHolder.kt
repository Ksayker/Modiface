package com.ksayker.modiface.presentation.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<I>(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: I)

    protected fun ifHasPosition(block: (Int) -> Unit) {
        val position = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            block(position)
        }
    }
}
