package com.ksayker.modiface.presentation.adapter.filter

import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.presentation.base.adapter.BaseAdapter
import com.ksayker.modiface.presentation.base.adapter.BaseItem

class FilterAdapter(onImageClocked: (filter: Filter) -> Unit) :
    BaseAdapter<FilterItem, BaseItem<FilterItem>, FilterViewHolder>(
        object : BaseItem<FilterItem>() {
            override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem) = oldItem == newItem
        }
    ) {

    init {
        addDelegate(
            FilterAdapterDelegate {
                onImageClocked(items[it].filter)
            }
        )
    }
}