package com.ksayker.modiface.presentation.base.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

abstract class BaseAdapter<I : BI, BI: Any, VH : RecyclerView.ViewHolder>(diffCallback: BaseItem<I>) :
    AsyncListDifferDelegationAdapter<I>(diffCallback) {

    @Suppress("UNCHECKED_CAST")
    protected fun addDelegate(delegate: AbsListItemAdapterDelegate<I, BI, VH>) {
        delegatesManager.addDelegate(delegate as AdapterDelegate<List<I>>)
    }
}