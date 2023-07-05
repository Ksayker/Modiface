package com.ksayker.modiface.presentation.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ksayker.modiface.databinding.ItemFilterBinding
import com.ksayker.modiface.presentation.base.adapter.BaseAdapterDelegate
import com.ksayker.modiface.presentation.base.adapter.BaseItem

class FilterAdapterDelegate(
    private val onImageClicked: (Int) -> Unit
) : BaseAdapterDelegate<FilterItem, BaseItem<FilterItem>, FilterViewHolder>() {
    override fun isForViewType(
        item: BaseItem<FilterItem>,
        items: MutableList<BaseItem<FilterItem>>,
        position: Int
    ) = item is FilterItem

    override fun onCreateViewHolder(parent: ViewGroup): FilterViewHolder =
        FilterViewHolder(
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onImageClicked
        )
}