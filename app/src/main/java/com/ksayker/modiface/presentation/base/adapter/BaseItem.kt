package com.ksayker.modiface.presentation.base.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

abstract class BaseItem<I: Any> :  DiffUtil.ItemCallback<I>() {
    override fun areItemsTheSame(oldItem: I, newItem: I) = oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: I, newItem: I) = oldItem == newItem
}