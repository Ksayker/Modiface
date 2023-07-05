package com.ksayker.modiface.presentation.base.adapter

import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

abstract class BaseAdapterDelegate<I : BI, BI: Any, VH : BaseViewHolder<I>> : AbsListItemAdapterDelegate<I, BI, VH>() {

    override fun onBindViewHolder(item: I, holder: VH, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}
